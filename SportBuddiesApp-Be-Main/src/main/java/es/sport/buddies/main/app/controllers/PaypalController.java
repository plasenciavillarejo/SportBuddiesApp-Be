package es.sport.buddies.main.app.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import es.sport.buddies.entity.app.dto.ApiPaypalDto;
import es.sport.buddies.entity.app.dto.PaypalDto;
import es.sport.buddies.entity.app.models.entity.Paypal;
import es.sport.buddies.entity.app.models.entity.ReservaUsuario;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IPaypalService;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;
import es.sport.buddies.entity.app.models.service.IUsuarioService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.PaypalException;
import es.sport.buddies.main.app.service.IPaypalMainService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/paypal")
public class PaypalController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  private static final String URLESTADOPAGO = "/api/main/paypal/estado/pago";
  
  @Autowired
  private IPaypalMainService paypalMainService;
  
  @Autowired
  private IReservaUsuarioService reservaUsuarioService;
  
  @Autowired
  private IPaypalService paypalService;
  
  @Autowired
  @Qualifier("externalWebClient")
  private WebClient.Builder externalWebClient;
  
  @Autowired
  private IUsuarioService usuarioService;
  
  /**
   * Servicio encargado de tramitar el pago con paypal
   * @param paypalDto
   * @return
   * @throws PaypalException
   */
  @PostMapping(value = "/crear/pago")
  public ResponseEntity<Map<String, String>> crearPagoPaypal(@RequestBody PaypalDto paypalDto) throws PaypalException {
    Map<String, String> response = new HashMap<>();
    try {
      Payment payment = paypalMainService.crearPago(paypalDto.getTotal(), paypalDto.getDivisa(), paypalDto.getMetodo(),
          paypalDto.getIntencion(), paypalDto.getDescripcion(),
          ConstantesMain.SPORTBUDDIESFE.concat(ConstantesMain.PAYPALCANCEL),
          ConstantesMain.SPORTBUDDIESFE);
      
      Optional<Links> approvalLink = payment.getLinks().stream()
          .filter(link -> link.getRel().equalsIgnoreCase(ConstantesMain.URLAPROBACION))
          .findFirst();
      
      if (approvalLink.isPresent()) {
        // Devolver el enlace de aprobación
        response.put(ConstantesMain.URLAPROBACION, approvalLink.get().getHref());
        return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
        // No se encontró la URL de aprobación
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }      
    } catch (Exception e) {
      LOGGER.error("Error {}", e.getMessage(), e.getCause());
      throw new PaypalException();
    }
  }
  
  /**
   * Servicio encargado de recibir el paymen y el payer para realizar la confirmación del pago
   * @param paymentId
   * @param payerId
   * @return
   * @throws PaypalException
   * @throws IOException
   */
  @GetMapping(value = "/estado/pago")
  public ResponseEntity<Map<String, String>> pagoCorrecto(@RequestParam("paymentId") String paymentId,
      @RequestParam("PayerID") String payerId, @RequestParam("idReserva") long idReservaUsuario) throws PaypalException {
    Map<String, String> mapResponse = new HashMap<>();
    try {
      Payment payment = paypalMainService.ejecutarPago(paymentId, payerId);

      if (payment.getState().equalsIgnoreCase(ConstantesMain.APPROVED)) {
        payment.getTransactions().forEach(transaction -> transaction.getRelatedResources()
            .forEach(related -> {
              Paypal paypal = Paypal.builder()
                  .total(Double.valueOf(related.getSale().getAmount().getTotal().replace(",", ".")))
                  .moneda(related.getSale().getAmount().getCurrency())
                  .reembolsado(false)
                  .fechaReembolso(null)
                  .build();

          Optional<Links> linkRefund = related.getSale().getLinks().stream()
              .filter(link -> link.getHref().endsWith(ConstantesMain.REFUND)).findFirst();
          
          ReservaUsuario res = reservaUsuarioService.findById(idReservaUsuario);
          Usuario usuario = usuarioService.findById(res.getUsuario().getIdUsuario()).orElse(null);
          
          if (linkRefund.isPresent()) {
            paypal.setUrlRefund(linkRefund.get().getHref());
            paypal.setReservaUsuario(ReservaUsuario.builder().idReserva(idReservaUsuario).build());
            paypal.setUsuario(usuario);
            try {
              guardarObjetoPaypal(paypal);
            } catch (PaypalException e) {
              LOGGER.error("Ha sucedido un error a la hora de almacenar los datos en la tabla PAYPAL");
            }
          }
        }));        
        // Una vez que se ha pagado la reserva, se actualiza la tabla y se indica el metodo de pago con el que se ha realiado 'Paypal'
        reservaUsuarioService.actualizarAbonoReserva(idReservaUsuario, ConstantesMain.METODOPAGOPAYPAL);
        mapResponse.put(ConstantesMain.SUCCESS, "Pago realizado correctamente");
      } else {
        mapResponse.put(ConstantesMain.ERRROR, "Pago incorrecto");
      }
      return new ResponseEntity<>(mapResponse,HttpStatus.OK);
    }catch (PayPalRESTException e) {
      throw new PaypalException();
    }
  }

  /**
   * Función encargada de guardar los datos recibidos de paypal
   * @param paypal
   * @throws PaypalException
   */
  private void guardarObjetoPaypal(Paypal paypal) throws PaypalException {
    try {
      LOGGER.info("Se procede almacenar los datos en la tabla PAYPAL");
      paypalService.guardar(paypal);
      LOGGER.info("Se han almacenado exitosamente");
    }catch (Exception e) {
      throw new PaypalException();
    }
  }

  /**
   * Función encargada de devolver el pago
   * @param idReservaUsuario
   * @return
   * @throws PaypalException
   */
  @PostMapping(value = "/devolver/pago")
  public ResponseEntity<Void> devolucionPaypal(@RequestParam("idReservaUsuario") long idReservaUsuario) throws PaypalException {
    try {
      Paypal paypal = paypalService.buscarReservaPagada(idReservaUsuario);
      if(paypal == null) {
        throw new PaypalException("La reserva con ID: "+ idReservaUsuario + " no existe");
      }

      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add(ConstantesMain.GRANTTYPE, ConstantesMain.CLIENTCREDENTIALS);
      
      // Bloqueamos el webBuilder ya que necesitamos la respuseta para trabajar con ella
      LOGGER.info("Se procede a obtener el token sobre la API de Paypal");
      ResponseEntity<ApiPaypalDto> apiPaypalTokenResponse = respuestaApiTokenPaypal(body).block();
      
      if (apiPaypalTokenResponse != null && apiPaypalTokenResponse.getStatusCode().is2xxSuccessful()) {
          LOGGER.info("Se a obtenido el token corectamente");
          LOGGER.info("Se procede a validar que el estado de la transaccion sea 'completed'");
          // Obtenemos el parametro a enviar:
          String [] parts = paypal.getUrlRefund().split(ConstantesMain.SLASH);
          
          Map<String, Object> estado = validarEstadoTransaccion(apiPaypalTokenResponse.getBody().getAccess_token(),
              parts[parts.length-2]).block();
          
          if(estado.get(ConstantesMain.STATE) == ConstantesMain.REFUNED) {
            throw new PaypalException("Error, la transaccion ya se abonado con anterioridad");
          }
          
          LOGGER.info("La transacción actualmente tiene un estado 'completed', se procede con el abono de la misma");
          ApiPaypalDto apiPaypalDto = apiPaypalTokenResponse.getBody();

          Map<String, Object> bodyDos = new HashMap<>();
          Map<String, String> amount = new HashMap<>();
          amount.put(ConstantesMain.TOTAL, String.valueOf(paypal.getTotal()));
          amount.put(ConstantesMain.CURRENCY, paypal.getMoneda());

          bodyDos.put(ConstantesMain.AMOUNT, amount);
          
          ResponseEntity<Void> respone = enviarDevolucion(bodyDos, apiPaypalDto.getAccess_token(), paypal.getUrlRefund()).block();
          if(respone.getStatusCode().is2xxSuccessful()) {
            LOGGER.info("Se ha realizado el abono corectamente, se procede actualizar el estado en la tabla");
            paypalService.actualizarReservaReembolsada(LocalDate.now(), paypal.getIdPaypal());
            LOGGER.info("Actualización existosa para el ID: '{}'", paypal.getIdPaypal());
          }
      } else {
        throw new PaypalException("Error al obtener el token de paypal");
      }     
    } catch (Exception e) {
      throw new PaypalException(e);
    }   
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
  
  /**
   * Función encargada de obtener el token desde la api de paypal 
   * @param body
   * @return
   */
  private Mono<ResponseEntity<ApiPaypalDto>> respuestaApiTokenPaypal(MultiValueMap<String, String> body) {
    return externalWebClient.build().post().uri(ConstantesMain.URLTOKENAPIPAYPAL)
        .body(BodyInserters.fromValue(body))
        .headers(headers -> {
          headers.setAccept((Collections.singletonList(MediaType.APPLICATION_JSON)));
          headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
          headers.setBasicAuth(ConstantesMain.CLIENTIDPAYPAL, ConstantesMain.CLIENTSERCRETPAYPAL);
        }).retrieve().toEntity(ApiPaypalDto.class);
  }

  /**
   * Función encargada de validar el estado de la transacción
   * @param token
   * @param identificador
   * @return
   */
  private Mono<Map<String, Object>> validarEstadoTransaccion(String token, String identificador) {
    return externalWebClient.build().get().uri(ConstantesMain.URLESTADOTRANSACCIONPAYPAL.concat(identificador))
        .headers(headers -> headers.setBearerAuth(token))
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
  }
  
  /**
   * Función encargda de enviar la devolución del dinero
   * @param body
   * @param token
   * @param url
   * @return
   */
  private Mono<ResponseEntity<Void>> enviarDevolucion(Map<String, Object> body, String token, String url) {
    return externalWebClient.build().post().uri(url)
    .body(BodyInserters.fromValue(body))
    .headers(headers -> headers.setBearerAuth(token))
    .retrieve()
    .toBodilessEntity();
  }
  
}

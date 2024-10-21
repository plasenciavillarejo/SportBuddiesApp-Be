package es.sport.buddies.main.app.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import es.sport.buddies.entity.app.dto.PaypalDto;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.PaypalException;
import es.sport.buddies.main.app.service.IPaypalService;

@RestController
@RequestMapping(value = "/paypal")
public class PaypalController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  private static final String URLESTADOPAGO = "/api/main/paypal/estado/pago";
  
  @Autowired
  private IPaypalService paypalService;
  
  @Autowired
  private IReservaUsuarioService reservaUsuarioService;
  
  @PostMapping(value = "/crear/pago")
  public ResponseEntity<Map<String, String>> crearPagoPaypal(@RequestBody PaypalDto paypalDto) throws PaypalException {
    Map<String, String> response = new HashMap<>();
    try {
      String urlCancel = ConstantesMain.SPORTBUDDIESFE.concat(ConstantesMain.PAYPALCANCEL);
      String urlSuccess = ConstantesMain.SPORTBUDDIESFE;
      
      Payment payment = paypalService.crearPago(paypalDto.getTotal(), paypalDto.getDivisa(), paypalDto.getMetodo(),
          paypalDto.getIntencion(), paypalDto.getDescripcion(),urlCancel, urlSuccess);
      
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
      @RequestParam("PayerID") String payerId, @RequestParam("idReserva") long idReserva) throws PaypalException {
    Map<String, String> mapResponse = new HashMap<>();
    try {
      Payment payment = paypalService.ejecutarPago(paymentId, payerId);
      if (payment.getState().equalsIgnoreCase("approved")) {
        // Una vez que se ha pagado la reserva, se actuliza la tabla
        reservaUsuarioService.actualizarAbonoReserva(idReserva);
        mapResponse.put(ConstantesMain.SUCCESS, "Pago realizado correctamente");
      } else {
        mapResponse.put(ConstantesMain.ERRROR, "Pago incorrecto");
      }
      return new ResponseEntity<>(mapResponse,HttpStatus.OK);
    }catch (PayPalRESTException e) {
      throw new PaypalException();
    }
  }
  

  
}

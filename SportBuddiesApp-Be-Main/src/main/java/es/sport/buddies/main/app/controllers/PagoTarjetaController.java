package es.sport.buddies.main.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentIntentCollection;
import com.stripe.param.PaymentIntentListParams;

import es.sport.buddies.entity.app.dto.StripeChargeDto;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.PagoTarjetaException;
import es.sport.buddies.main.app.service.IPagoTarjetaService;


@RestController
@RequestMapping(value = "/tarjeta")
public class PagoTarjetaController {

  @Autowired
  private IPagoTarjetaService pagoTarjetaService;

  @Autowired
  @Qualifier("externalWebClient")
  private WebClient.Builder externalWebClient;
  
  @PostMapping(value = "/realizar/pago")
  public ResponseEntity<Map<String, String>> realizarCargo(@RequestBody StripeChargeDto stripeChargeDto) throws PagoTarjetaException {
    Map<String, String> confirmacionPago = null;
    try {
      confirmacionPago = pagoTarjetaService.realizarCargo(stripeChargeDto);
    } catch (Exception e) {
      throw new PagoTarjetaException(e);
    }
    return new ResponseEntity<>(confirmacionPago, HttpStatus.OK);
  }
  
  @PostMapping(value = "/devolver/pago")
  public ResponseEntity<Map<String, String>> devolverPago(@RequestParam String paymentIntentId) throws PagoTarjetaException {
    Map<String, String> devolverPago = null;
    try {
      devolverPago = pagoTarjetaService.devolver(paymentIntentId);
    } catch (Exception e) {
      throw new PagoTarjetaException(e);
    }
    return new ResponseEntity<>(devolverPago, HttpStatus.OK);
  }


  @GetMapping(value = "/listar/pagos")
  public ResponseEntity<Object> listarPagos(@RequestParam long numeroLimite) throws PagoTarjetaException{
    PaymentIntentCollection paymentIntents = null;
    try {
      /*paymentIntents = PaymentIntent.list(
          PaymentIntentListParams.builder()
          .setLimit(numeroLimite) .build());*/
    } catch (Exception e) {
      throw new PagoTarjetaException(e);
    }
    return new ResponseEntity<>(externalWebClient.build().get().uri("https://api.stripe.com/v1/payment_intents")
        .headers(headers -> headers.setBearerAuth(ConstantesMain.STRIPESECRETKEY))
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<Object>() {}), HttpStatus.OK);
  }
  
  
}

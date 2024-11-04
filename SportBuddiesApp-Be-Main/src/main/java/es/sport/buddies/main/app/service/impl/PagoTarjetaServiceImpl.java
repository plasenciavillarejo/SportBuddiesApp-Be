package es.sport.buddies.main.app.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;

import es.sport.buddies.entity.app.dto.StripeChargeDto;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.PagoTarjetaException;
import es.sport.buddies.main.app.service.IPagoTarjetaService;

@Service
public class PagoTarjetaServiceImpl implements IPagoTarjetaService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);

  public PagoTarjetaServiceImpl() {
    Stripe.apiKey = ConstantesMain.STRIPESECRETKEY;
  }
    
  
  @Override
  public Map<String, String> realizarCargo(StripeChargeDto stripeChargeDto) throws PagoTarjetaException, StripeException {
    // Establece la clave justo antes de hacer la llamada
    
    LOGGER.info("Se procede a generar el PaymentIntentCreateParams con los datos de la tarjeta");
    PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
        // Convertimos el monto a centavos ya que Stripe lo proceso de está forma
        .setAmount(stripeChargeDto.getCantidad() * 100)
        .setCurrency(stripeChargeDto.getDivisa())
        .setPaymentMethod(stripeChargeDto.getMetodoPago())
        .setConfirm(true)
        .setDescription(stripeChargeDto.getDescripcion())
        //.setReturnUrl("front-angular para devolver el retorno")
        .setAutomaticPaymentMethods(
            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                .setEnabled(true)
                .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
                .build())
        .build();
    
    PaymentIntent paymentIntent = PaymentIntent.create(params);
    return Map.of(
        "clientSecret", paymentIntent.getClientSecret());
  }

  @Override
  public Map<String, String> devolver(String paymentIntentId) throws PagoTarjetaException, StripeException {
    // Creación del reembolso
    RefundCreateParams params = RefundCreateParams.builder()
        .setPaymentIntent(paymentIntentId) // También puedes usar .setCharge(chargeId) si tienes el charge_id
        .build();

    Refund refund = Refund.create(params);

    return Map.of(
        "refundId", refund.getId(),
        "status", refund.getStatus());
  }

}

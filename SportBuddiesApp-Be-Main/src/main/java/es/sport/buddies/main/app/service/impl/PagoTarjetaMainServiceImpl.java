package es.sport.buddies.main.app.service.impl;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;

import es.sport.buddies.entity.app.dto.StripeChargeDto;
import es.sport.buddies.entity.app.models.entity.PagoTarjeta;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IPagoTarjetaService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.PagoTarjetaException;
import es.sport.buddies.main.app.service.IPagoTarjetaMainService;

@Service
public class PagoTarjetaMainServiceImpl implements IPagoTarjetaMainService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);

  @Autowired
  private IPagoTarjetaService pagoTarjetaService;
  
  public PagoTarjetaMainServiceImpl() {
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
    
    PagoTarjeta pagoTarjeta = PagoTarjeta.builder()
        .moneda(stripeChargeDto.getDivisa())
        .idDevolucion(paymentIntent.getId())
        .fechaCobro(LocalDate.now())
        .usuario(Usuario.builder()
            .idUsuario(stripeChargeDto.getIdUsuario())
            .build())
        .fechaDevolucion(null)
        .reembolsado(false)
        .build(); 
    
    guardarPago(pagoTarjeta);

    return Map.of(
        "clientSecret", paymentIntent.getClientSecret());
  }

  /**
   * Función encargada de almacenar el pago de la tarjeta
   * @param pagoTarjeta
   * @throws PagoTarjetaException
   */
  private void guardarPago(PagoTarjeta pagoTarjeta) throws PagoTarjetaException {
    try {
      LOGGER.info("Se procede a guardar el pago");
      pagoTarjetaService.guardarPagoTarjeta(pagoTarjeta);
      LOGGER.info("Pago almacenado exitosamente");
    } catch (Exception e) {
      throw new PagoTarjetaException(e);
    }
  }

  @Override
  public Map<String, String> devolver(String paymentIntentId) throws PagoTarjetaException, StripeException {    
    PagoTarjeta pagoTarjeta = pagoTarjetaService.findByIdDevolucion(paymentIntentId);
    Refund refund = null;
    if(pagoTarjeta != null && !pagoTarjeta.isReembolsado() && pagoTarjeta.getFechaDevolucion() == null) {
      // Creación del reembolso
      RefundCreateParams params = RefundCreateParams.builder()
          .setPaymentIntent(paymentIntentId) // También puedes usar .setCharge(chargeId) si tienes el charge_id
          .build();

      refund = Refund.create(params);
      pagoTarjeta.setFechaDevolucion(LocalDate.now());
      actualizarPagoTarjeta(pagoTarjeta.getFechaDevolucion(), paymentIntentId);
    }

    return Map.of(
        "refundId", refund.getId(),
        "status", refund.getStatus());
  }

  
  private void actualizarPagoTarjeta(LocalDate fechaReembolso, String paymentIntentId) throws PagoTarjetaException {
    try {
      LOGGER.info("Se procede a actualizar la fecha de reembolso");
      pagoTarjetaService.actualizarPagoReembolso(fechaReembolso,paymentIntentId);
      LOGGER.info("Pago actualizado exitosamente");
    } catch (Exception e) {
      throw new PagoTarjetaException(e);
    }
  }
  
}

package es.sport.buddies.main.app.service;

import java.util.Map;

import com.stripe.exception.StripeException;

import es.sport.buddies.entity.app.dto.StripeChargeDto;
import es.sport.buddies.main.app.exceptions.PagoTarjetaException;

public interface IPagoTarjetaMainService {
 
  public Map<String, String> realizarCargo(StripeChargeDto stripeChargeDto) throws PagoTarjetaException, StripeException;
  
  public Map<String, String> devolver(String paymentIntentId) throws PagoTarjetaException, StripeException;
  
}

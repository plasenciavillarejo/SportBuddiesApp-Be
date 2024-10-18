package es.sport.buddies.main.app.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface IPaypalService {

  public Payment crearPago(Double total, String divisa, String metodo, String intencion, String descripcion, String urlCancel, String urlSuccess) throws PayPalRESTException;
  
  public Payment ejecutarPago(String paymentId, String payerId) throws PayPalRESTException;
  
}

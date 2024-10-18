package es.sport.buddies.main.app.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import es.sport.buddies.main.app.service.IPaypalService;

@Service
public class PaypalServiceImpl implements IPaypalService {

  @Autowired
  private APIContext apiContext;
  
  @Override
  public Payment crearPago(Double total, String divisa, String metodo, String intencion, String descripcion, String urlCancel, String urlSuccess) throws PayPalRESTException {
    
    Amount amount = new Amount();
    amount.setCurrency(divisa);
    amount.setTotal(String.format((Locale.forLanguageTag(divisa)), "%.2f", total)) ; // Tipo formato 9.99$ - 9,99 €
    
    Transaction transaction = new Transaction();
    transaction.setDescription(descripcion);
    transaction.setAmount(amount);
    
    List<Transaction> transactions = Arrays.asList(transaction);
    
    Payer payer = new Payer();
    payer.setPaymentMethod(metodo);
    
    Payment payment = new Payment();
    payment.setIntent(intencion);
    payment.setPayer(payer);
    payment.setTransactions(transactions);
    
    RedirectUrls redirectUrls = new RedirectUrls();
    redirectUrls.setCancelUrl(urlCancel);
    redirectUrls.setReturnUrl(urlSuccess);
    
    payment.setRedirectUrls(redirectUrls);
    
    return payment.create(apiContext);
  }

  @Override
  public Payment ejecutarPago(String paymentId, String payerId) throws PayPalRESTException {
    
    Payment payment = new Payment();
    payment.setId(paymentId);
    
    PaymentExecution paymenExecution = new PaymentExecution();
    paymenExecution.setPayerId(payerId);
    
    return payment.execute(apiContext, paymenExecution);
  }

}

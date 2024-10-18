package es.sport.buddies.main.app.exceptions;

import java.io.Serializable;

public class PaypalException extends Exception implements Serializable {

  public PaypalException() {
    super();
  }

  public PaypalException(String mensaje) {
    super(mensaje);
  }

  public PaypalException(Throwable t) {
    super(t);
  }

  public PaypalException(String mensaje, Throwable t) {
    super(mensaje, t);
  }

  private static final long serialVersionUID = -2807874397482453800L;

}

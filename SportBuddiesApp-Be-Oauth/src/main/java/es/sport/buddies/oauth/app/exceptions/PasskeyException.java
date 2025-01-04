package es.sport.buddies.oauth.app.exceptions;

import java.io.Serializable;

public class PasskeyException extends Exception implements Serializable {

  public PasskeyException() {
    super();
  }

  public PasskeyException(String mensaje) {
    super(mensaje);
  }

  public PasskeyException(Throwable t) {
    super(t);
  }

  public PasskeyException(String mensaje, Throwable t) {
    super(mensaje, t);
  }

  private static final long serialVersionUID = -5310176211769582600L;

}

package es.sport.buddies.main.app.exceptions;

import java.io.Serializable;

public class PagoTarjetaException extends Exception implements Serializable {

  public PagoTarjetaException() {
    super();
  }

  public PagoTarjetaException(String mensaje) {
    super(mensaje);
  }

  public PagoTarjetaException(Throwable t) {
    super(t);
  }

  public PagoTarjetaException(String mensaje, Throwable t) {
    super(mensaje, t);
  }

  private static final long serialVersionUID = -5521558686291689841L;

}

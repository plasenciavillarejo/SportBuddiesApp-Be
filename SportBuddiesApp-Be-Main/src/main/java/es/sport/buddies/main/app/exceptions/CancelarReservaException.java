package es.sport.buddies.main.app.exceptions;

import java.io.Serializable;

public class CancelarReservaException extends Exception implements Serializable {

  public CancelarReservaException() {
    super();
  }

  public CancelarReservaException(String mensaje) {
    super(mensaje);
  }

  public CancelarReservaException(Throwable t) {
    super(t);
  }

  public CancelarReservaException(String mensaje, Throwable t) {
    super(mensaje, t);
  }

  private static final long serialVersionUID = 2338660778967297226L;

}

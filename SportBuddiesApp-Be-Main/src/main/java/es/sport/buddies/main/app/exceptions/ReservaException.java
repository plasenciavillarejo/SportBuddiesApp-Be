package es.sport.buddies.main.app.exceptions;

import java.io.Serializable;

public class ReservaException extends Exception implements Serializable {

  public ReservaException() {
    super();
  }

  public ReservaException(String mensaje) {
    super(mensaje);
  }

  public ReservaException(Throwable t) {
    super(t);
  }

  public ReservaException(String mensaje, Throwable t) {
    super(mensaje, t);
  }

  private static final long serialVersionUID = -5333997883956835740L;

}

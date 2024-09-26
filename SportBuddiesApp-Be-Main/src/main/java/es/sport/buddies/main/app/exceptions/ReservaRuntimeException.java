package es.sport.buddies.main.app.exceptions;

import java.io.Serializable;

public class ReservaRuntimeException extends RuntimeException implements Serializable {

  public ReservaRuntimeException() {
    super();
  }

  public ReservaRuntimeException(String message) {
    super(message);
  }

  public ReservaRuntimeException(Throwable cause) {
    super(cause);
  }

  public ReservaRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  private static final long serialVersionUID = 5517703522110170770L;

}

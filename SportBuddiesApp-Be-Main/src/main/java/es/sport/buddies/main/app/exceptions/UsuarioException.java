package es.sport.buddies.main.app.exceptions;

import java.io.Serializable;

public class UsuarioException extends Exception implements Serializable {

  public UsuarioException() {
    super();
  }

  public UsuarioException(String mensaje) {
    super(mensaje);
  }

  public UsuarioException(Throwable t) {
    super(t);
  }

  public UsuarioException(String mensaje, Throwable t) {
    super(mensaje, t);
  }

  private static final long serialVersionUID = -1150931967400876512L;

}

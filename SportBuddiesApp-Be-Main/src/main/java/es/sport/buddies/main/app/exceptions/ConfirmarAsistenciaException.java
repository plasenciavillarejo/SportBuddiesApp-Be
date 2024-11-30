package es.sport.buddies.main.app.exceptions;

import java.io.Serializable;

public class ConfirmarAsistenciaException  extends Exception implements Serializable {

  public ConfirmarAsistenciaException() {
    super();
  }

  public ConfirmarAsistenciaException(String mensaje) {
    super(mensaje);
  }

  public ConfirmarAsistenciaException(Throwable t) {
    super(t);
  }

  public ConfirmarAsistenciaException(String mensaje, Throwable t) {
    super(mensaje, t);
  }
  
  private static final long serialVersionUID = 6447275242116312404L;

  
}

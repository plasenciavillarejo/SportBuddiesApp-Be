package es.sport.buddies.main.app.exceptions;

import java.io.Serializable;

public class CrearReservaException extends Exception implements Serializable {

  public CrearReservaException() {
    super();
  }

  public CrearReservaException(String mensaje) {
    super(mensaje);
  }

  public CrearReservaException(Throwable t) {
    super(t);
  }

  public CrearReservaException(String mensaje, Throwable t) {
    super(mensaje, t);
  }
  
  private static final long serialVersionUID = -7810930008383048387L;

}

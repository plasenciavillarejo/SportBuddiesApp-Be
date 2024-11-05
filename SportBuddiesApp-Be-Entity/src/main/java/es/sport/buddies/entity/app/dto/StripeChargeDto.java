package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class StripeChargeDto implements Serializable {
 
  private double cantidad;
 
  private String divisa;
  
  private String metodoPago;
  
  private String descripcion;
  
  private long idUsuario;
  
  private long idReservaUsuario;
  
  private static final long serialVersionUID = 366227233608321036L;

}

package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class StripeChargeDto implements Serializable {
 
  private long cantidad;
 
  private String divisa;
  
  private String metodoPago;
  
  private String descripcion;
  
  private static final long serialVersionUID = 366227233608321036L;

}

package es.sport.buddies.entity.app.dto;

import lombok.Data;

@Data
public class PaypalDto {

  private Double total; 
  
  private String divisa;
 
  private String metodo;
  
  private String intencion;
  
  private String descripcion;
  
}

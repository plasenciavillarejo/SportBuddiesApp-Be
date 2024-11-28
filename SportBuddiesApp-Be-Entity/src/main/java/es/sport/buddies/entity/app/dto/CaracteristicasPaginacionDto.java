package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CaracteristicasPaginacionDto implements Serializable {

  private int pagina;
  
  private int tamanioPagina;
  
  private String campoOrden;
  
  private int orden;
  
  private static final long serialVersionUID = -8257140274353707213L;
  
}

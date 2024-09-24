package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ReservaActividadDto implements Serializable {

  private Date fechaReserva;
  
  private String actividad;
  
  private String provincia;
  
  private String municipio;
  
  private static final long serialVersionUID = 722868750680709837L;

}

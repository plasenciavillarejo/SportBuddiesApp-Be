package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ListadoReservaActividadDto implements Serializable {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date fechaReserva;

  private String actividad;

  private String provincia;

  private String municipio;
  
  private static final long serialVersionUID = 7339027390310558980L;

}

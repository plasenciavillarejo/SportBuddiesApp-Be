package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListadoReservaActividadDto implements Serializable {

  //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate fechaReserva;

  private String actividad;

  private String provincia;

  private String municipio;
  
  private static final long serialVersionUID = 7339027390310558980L;

}

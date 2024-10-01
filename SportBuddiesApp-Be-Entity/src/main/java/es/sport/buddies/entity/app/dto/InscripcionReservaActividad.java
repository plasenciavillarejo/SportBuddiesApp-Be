package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class InscripcionReservaActividad implements Serializable {
  
  private long idReservaActividad;
  
  private LocalDate fechaReserva;
  
  private LocalTime horaInicioReserva;
  
  private LocalTime horaFinReserva;
    
  private long idUsuario;
  
  private long idDeporte;
  
  private String abonado;
  
  private static final long serialVersionUID = -4930926324910987759L;

}

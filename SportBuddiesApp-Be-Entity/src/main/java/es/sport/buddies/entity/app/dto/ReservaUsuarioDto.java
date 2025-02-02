package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import lombok.Data;

@Data
public class ReservaUsuarioDto implements Serializable {

  private long idReserva;
  
  private Date fechaReserva;

  private LocalTime horaInicioReserva;

  private LocalTime horaFinReserva;

  private UsuarioDto usuarioReservaDto;

  private DeporteDto deporteReservaDto;
  
  private ReservaActividadDto reservaActividadDto;

  private boolean abonado;
  
  private String metodoPago;
  
  private static final long serialVersionUID = -3497616209985551630L;

}

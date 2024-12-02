package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ConfirmarUsuarioDto implements Serializable {
  
  private long idUsuario;
  
  private long idReservaActividad;

  private CaracteristicasPaginacionDto caracteristicasPaginacion;
  
  private LocalDate fechaReserva;
  
  private LocalTime horaInicio;
  
  private LocalTime horaFin;
  
  private String nombreUsuario;
  
  private String apellidoUsuario;
    
  private String actividad;
  
  private static final long serialVersionUID = 44855331688008910L;

}

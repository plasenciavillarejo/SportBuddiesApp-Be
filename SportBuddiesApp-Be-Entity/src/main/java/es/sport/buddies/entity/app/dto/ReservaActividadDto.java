package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ReservaActividadDto implements Serializable {

  private long idReservaActividad;
  
  private Date fechaReserva;
  
  private LocalTime horaInicio;
  
  private LocalTime horaFin;
  
  private String actividad;
  
  private String provincia;
  
  private String municipio;
  
  private UsuarioDto usuarioActividadDto;
  
  private long usuariosMaxRequeridos;
  
  private long plazasRestantes;
  
  private List<String> requerimientos;

  private String urgencia;
  
  private double abonoPista;
  
  private static final long serialVersionUID = 722868750680709837L;

}

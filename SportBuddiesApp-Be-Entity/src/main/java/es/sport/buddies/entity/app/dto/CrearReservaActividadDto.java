package es.sport.buddies.entity.app.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class CrearReservaActividadDto {

  //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate fechaReserva;
  
  private LocalTime horaInicio;
  
  private LocalTime horaFin;
  
  private List<String> requerimientos;
  
  private long usuariosMaxRequeridos;
  
  private long plazasRestantes;
  
  private long idUsuarioActividadDto;
  
  private String actividad;
  
  private String direccion;
  
  private String provincia;
  
  private String municipio;
    
  private long codigoPostal;

  private String urgencia;
  
  private double abonoPista;
  
}

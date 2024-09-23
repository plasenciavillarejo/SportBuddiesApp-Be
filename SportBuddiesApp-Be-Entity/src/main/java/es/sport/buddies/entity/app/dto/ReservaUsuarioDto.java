package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

public class ReservaUsuarioDto implements Serializable {

  private long idReserva;

  private Date fechaReserva;

  private LocalTime horaInicioReserva;

  private LocalTime horaFinReserva;

  private String observaciones;

  private UsuarioDto usuarioReservaDto;

  private DeporteDto deporteReservaDto;

  public long getIdReserva() {
    return idReserva;
  }

  public void setIdReserva(long idReserva) {
    this.idReserva = idReserva;
  }

  public Date getFechaReserva() {
    return fechaReserva;
  }

  public void setFechaReserva(Date fechaReserva) {
    this.fechaReserva = fechaReserva;
  }

  public LocalTime getHoraInicioReserva() {
    return horaInicioReserva;
  }

  public void setHoraInicioReserva(LocalTime horaInicioReserva) {
    this.horaInicioReserva = horaInicioReserva;
  }

  public LocalTime getHoraFinReserva() {
    return horaFinReserva;
  }

  public void setHoraFinReserva(LocalTime horaFinReserva) {
    this.horaFinReserva = horaFinReserva;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public UsuarioDto getUsuarioReservaDto() {
    return usuarioReservaDto;
  }

  public void setUsuarioReservaDto(UsuarioDto usuarioReservaDto) {
    this.usuarioReservaDto = usuarioReservaDto;
  }

  public DeporteDto getDeporteReservaDto() {
    return deporteReservaDto;
  }

  public void setDeporteReservaDto(DeporteDto deporteReservaDto) {
    this.deporteReservaDto = deporteReservaDto;
  }

  private static final long serialVersionUID = -3497616209985551630L;

}

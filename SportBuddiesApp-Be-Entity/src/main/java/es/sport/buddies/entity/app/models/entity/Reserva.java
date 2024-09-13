package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "reservas")
public class Reserva implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idReserva")
  private long idReserva;

  @Column(name = "fechaReserva")
  @Temporal(TemporalType.DATE)
  // @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date fechaReserva;

  @Column(name = "horaInicioReserva")
  private LocalTime horaInicioReserva;

  @Column(name = "horaFinReserva")
  private LocalTime horaFinReserva;

  @Column(name = "observaciones", length = 200)
  private String observaciones;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idUsuarioReserva")
  private Usuario usuarioReserva;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idDeporteReserva")
  private Deporte deporteReserva;

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

  public Usuario getUsuarioReserva() {
    return usuarioReserva;
  }

  public void setUsuarioReserva(Usuario usuarioReserva) {
    this.usuarioReserva = usuarioReserva;
  }

  public Deporte getDeporteReserva() {
    return deporteReserva;
  }

  public void setDeporteReserva(Deporte deporteReserva) {
    this.deporteReserva = deporteReserva;
  }




  private static final long serialVersionUID = 4230007596971428886L;

}

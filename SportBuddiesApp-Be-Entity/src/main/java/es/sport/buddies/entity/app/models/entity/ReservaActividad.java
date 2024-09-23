package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

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
@Table(name = "reservas_actividad")
public class ReservaActividad implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_reserva_actividad")
  private long idReservaActividad;

  @Column(name = "fecha_reserva")
  @Temporal(TemporalType.DATE)
  private Date fechaReserva;

  @Column(name = "hora_inicio")
  private LocalTime horaInicio;

  @Column(name = "hora_fin")
  private LocalTime horaFin;

  @Column(name = "requerimientos")
  private List<String> requerimientos;

  @Column(name = "usuarios_max_requeridos")
  private long usuariosMaxRequeridos;

  @Column(name = "deporte")
  private String deporte;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_actividad_fk", nullable = false)
  private Usuario usuarioActividad;

  @Column(name = "direccion")
  private String direccion;

  @Column(name = "provincia")
  private String provincia;

  @Column(name = "municipio")
  private String municipio;

  @Column(name = "codigoPostal")
  private long codigoPostal;

  public long getIdReservaActividad() {
    return idReservaActividad;
  }

  public void setIdReservaActividad(long idReservaActividad) {
    this.idReservaActividad = idReservaActividad;
  }

  public Date getFechaReserva() {
    return fechaReserva;
  }

  public void setFechaReserva(Date fechaReserva) {
    this.fechaReserva = fechaReserva;
  }

  public LocalTime getHoraInicio() {
    return horaInicio;
  }

  public void setHoraInicio(LocalTime horaInicio) {
    this.horaInicio = horaInicio;
  }

  public LocalTime getHoraFin() {
    return horaFin;
  }

  public void setHoraFin(LocalTime horaFin) {
    this.horaFin = horaFin;
  }

  public List<String> getRequerimientos() {
    return requerimientos;
  }

  public void setRequerimientos(List<String> requerimientos) {
    this.requerimientos = requerimientos;
  }

  public long getUsuariosMaxRequeridos() {
    return usuariosMaxRequeridos;
  }

  public void setUsuariosMaxRequeridos(long usuariosMaxRequeridos) {
    this.usuariosMaxRequeridos = usuariosMaxRequeridos;
  }

  public String getDeporte() {
    return deporte;
  }

  public void setDeporte(String deporte) {
    this.deporte = deporte;
  }

  public Usuario getUsuarioActividad() {
    return usuarioActividad;
  }

  public void setUsuarioActividad(Usuario usuarioActividad) {
    this.usuarioActividad = usuarioActividad;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public String getMunicipio() {
    return municipio;
  }

  public void setMunicipio(String municipio) {
    this.municipio = municipio;
  }

  public long getCodigoPostal() {
    return codigoPostal;
  }

  public void setCodigoPostal(long codigoPostal) {
    this.codigoPostal = codigoPostal;
  }

  private static final long serialVersionUID = -6173273864401369841L;

}

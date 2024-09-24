package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import es.sport.buddies.entity.app.json.converter.ListToJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
  @Convert(converter = ListToJsonConverter.class)
  private List<String> requerimientos;

  @Column(name = "usuarios_max_requeridos")
  private long usuariosMaxRequeridos;

  @Column(name = "actividad")
  private String actividad;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_actividad_fk", nullable = false)
  private Usuario usuarioActividad;

  @Column(name = "direccion")
  private String direccion;

  @Column(name = "provincia")
  private String provincia;

  @Column(name = "municipio")
  private String municipio;

  @Column(name = "codigo_postal")
  private long codigoPostal;

  private static final long serialVersionUID = -6173273864401369841L;

}

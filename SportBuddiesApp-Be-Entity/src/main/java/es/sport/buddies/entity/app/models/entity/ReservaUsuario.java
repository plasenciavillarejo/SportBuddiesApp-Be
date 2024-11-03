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
@Table(name = "reservas_usuario")
public class ReservaUsuario implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_reserva")
  private long idReserva;

  @Column(name = "fecha_reserva")
  @Temporal(TemporalType.DATE)
  // @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date fechaReserva;

  @Column(name = "hora_inicio_reserva")
  private LocalTime horaInicioReserva;

  @Column(name = "hora_fin_reserva")
  private LocalTime horaFinReserva;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_reserva_fk")
  private Usuario usuario;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "deporte_reserva_fk")
  private Deporte deporte;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reserva_actividad_fk")
  private ReservaActividad reservaActividad;
  
  @Column(name = "abonado")
  private boolean abonado;
  
  @Column(name = "metodo_pago", length = 100)
  private String metodoPago;
  
  private static final long serialVersionUID = 4230007596971428886L;

}

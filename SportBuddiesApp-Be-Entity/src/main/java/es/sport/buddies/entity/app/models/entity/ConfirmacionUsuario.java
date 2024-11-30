package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "confirmacion_usuarios")
public class ConfirmacionUsuario implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_confirmacion")
  private long idConfirmacion;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_fk", nullable = false)
  private Usuario usuario;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reserva_actividad_fk")
  private ReservaActividad reservaActividad;
  
  private static final long serialVersionUID = -353740012249140893L;

}

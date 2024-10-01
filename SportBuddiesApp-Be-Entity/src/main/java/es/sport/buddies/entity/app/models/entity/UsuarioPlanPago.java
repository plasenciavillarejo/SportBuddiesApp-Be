package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario_plan_pago")
public class UsuarioPlanPago implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario_plan_pago")
  private long idUsuarioPlanPago;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_plan_pago_suscripcion_fk")
  private Suscripcion suscripcion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "plan_pago_fk")
  private PlanPago planPago;

  @Column(name = "reservas_restantes")
  private long reservasRestantes;

  @Column(name = "fecha_renovacion")
  private Date fechaRenovacion;

  private static final long serialVersionUID = -1985863634743354198L;

}

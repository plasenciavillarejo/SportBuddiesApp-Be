package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "planes_de_pago")
public class PlanPago implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_plan_pago")
  private long idPlanPago;

  @Column(name = "nombre_plan", length = 50)
  private String nombrePlan;

  @Column(name = "limite_reservas")
  private long limiteReservas;

  @Column(name = "precio_plan")
  private BigDecimal precioPlan;

  private static final long serialVersionUID = -3721897825135724894L;

}

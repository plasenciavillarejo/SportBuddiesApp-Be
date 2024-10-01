package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suscripciones")
public class Suscripcion implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_sucripcion")
  private long idSuscripcion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "suscripcion_usuario_fk")
  private Usuario usuario;

  @Column(name = "fecha_inicio")
  @Temporal(TemporalType.DATE)
  private Date fechaInicio;

  @Column(name = "fecha_fin")
  @Temporal(TemporalType.DATE)
  private Date fechaFin;

  @Column(name = "precio_total")
  private BigDecimal precioTotal;

  @Column(name = "metodo_pago")
  private String metodoPago;

  @Column(name = "estado_pago")
  private String estadoPago;

  private static final long serialVersionUID = -626223374324907081L;

}

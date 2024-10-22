package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "paypal")
public class Paypal implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_paypal")
  private long idPaypal;
  
  @Column(name = "url_refund", length = 255)
  private String urlRefund;
  
  @Column(name = "total")
  private double total;
  
  @Column(name = "moneda", length = 100)
  private String moneda;
  
  @Column(name = "reembolsado")
  private boolean reembolsado;
  
  @Column(name = "fecha_reembolso")
  private LocalDate fechaReembolso;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reserva_usuario_fk", nullable = false)
  private ReservaUsuario reservaUsuario;
  
  
  private static final long serialVersionUID = -1453742027484417035L;

}

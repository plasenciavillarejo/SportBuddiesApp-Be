package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;
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
@Table(name = "pago_tarjeta")
public class PagoTarjeta implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_pago_tarjeta")
  private long idPagoTarjeta;
  
  @Column(name = "id_devolucion")
  private String idDevolucion;
  
  @Column(name = "moneda")
  private String moneda;
  
  @Column(name = "fecha_cobro")
  private LocalDate fechaCobro;

  @Column(name = "fecha_devolucion")
  private LocalDate fechaDevolucion;
  
  @Column(name = "reembolsado")
  private boolean reembolsado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_fk")
  private Usuario usuario;
    
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reserva_usuario_fk")
  private ReservaUsuario reservaUsuario;
  
  private static final long serialVersionUID = 8573793276849355159L;
  
}

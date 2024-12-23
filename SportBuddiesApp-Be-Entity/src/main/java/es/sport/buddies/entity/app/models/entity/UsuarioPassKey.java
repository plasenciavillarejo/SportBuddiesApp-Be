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

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios_passkey")
public class UsuarioPassKey implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario_passkey")
  private long idUsuarioPasskey;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuarios_fk")
  private Usuario usuarios;

  @Column(name = "credencial_id")
  private String credencialId;
  
  @Column(name = "llave_publica")
  private String llavePublica;
  
  @Column(name = "algoritmo")
  private String algoritmo;
  
  @Column(name = "fechaCreacion")
  private LocalDate fechaCreacion;
  
  private static final long serialVersionUID = -1944648347458748494L;
  
}

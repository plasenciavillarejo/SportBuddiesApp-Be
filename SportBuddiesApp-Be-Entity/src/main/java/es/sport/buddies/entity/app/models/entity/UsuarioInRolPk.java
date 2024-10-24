package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class UsuarioInRolPk implements Serializable {

  @Column(name = "usuario_id")
  private long usuarioId;

  @Column(name = "role_id")
  private long roleId;
  
  private static final long serialVersionUID = 703885375740938961L;

}

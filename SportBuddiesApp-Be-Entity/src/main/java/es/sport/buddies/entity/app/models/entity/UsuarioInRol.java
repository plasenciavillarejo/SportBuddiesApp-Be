package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "usuarios_in_role")
public class UsuarioInRol implements Serializable {

  @EmbeddedId
  private UsuarioInRolPk usuarioInRolPk;
  
  private static final long serialVersionUID = 3875626160461010054L;
  
}

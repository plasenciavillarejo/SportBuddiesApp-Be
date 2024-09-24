package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UsuarioDto implements Serializable {

  private long idUsuario;

  private String nombreUsuario;

  private static final long serialVersionUID = -5083569157036242898L;

}

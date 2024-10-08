package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class UsernameAuthenticationDto extends UsernamePasswordAuthenticationToken implements Serializable {

  private UsuarioDto usuarioDto;
  
  public UsernameAuthenticationDto(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }

  private static final long serialVersionUID = -6302132390404439937L;
  
}

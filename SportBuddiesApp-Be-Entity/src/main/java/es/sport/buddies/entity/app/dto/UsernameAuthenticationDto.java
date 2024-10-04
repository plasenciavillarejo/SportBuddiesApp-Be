package es.sport.buddies.entity.app.dto;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class UsernameAuthenticationDto extends UsernamePasswordAuthenticationToken {

  private UsuarioDto usuarioDto;
  
  public UsernameAuthenticationDto(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }

  private static final long serialVersionUID = -6135476417113708181L;

  
}

package es.sport.buddies.oauth.app.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.dto.UsernameAuthenticationDto;
import es.sport.buddies.entity.app.dto.UsuarioDto;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IUsuarioService;
import es.sport.buddies.oauth.app.constantes.ConstantesApp;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailServiceImpl.class);
  
  private IUsuarioService usuarioService;
  
	// Por defecto no agrega la inyección de las depenencias, para hacerlo, lo inyectamos desde SecurityConfig.java mediante su constructor
	public UserDetailServiceImpl(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			LOGGER.info("Se procede a buscar al usuario: {}", username);
			Usuario usuario = usuarioService.findByNombreUsuario(username);
			List<GrantedAuthority> authorities = usuario.getRoles().stream()
		             .map(role -> (GrantedAuthority) 
		 		    		new SimpleGrantedAuthority(!role.getNombreRol().contains(ConstantesApp.ROLE) 
		 		    				? ConstantesApp.ROLE+ role.getNombreRol() : role.getNombreRol()))
		             .peek(authority -> LOGGER.info("Rol identificado {}, ", authority.getAuthority()))
		             .toList();
			LOGGER.info("Se ha localizado al usuario exitosamente, se procede almancenaro en el contexto de SpringSecurity");
			
			UsernameAuthenticationDto userAuthentication = new UsernameAuthenticationDto(usuario,null, authorities);
			UsuarioDto usuDto = new UsuarioDto();
			usuDto.setIdUsuario(usuario.getIdUsuario());
			usuDto.setNombreUsuario(usuario.getNombreUsuario());			
			userAuthentication.setUsuarioDto(usuDto);
						
			SecurityContextHolder.getContext().setAuthentication(userAuthentication);

			return new User(usuario.getNombreUsuario(),usuario.getPassword(),usuario.getEnabled(),true,true,true,authorities);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Usuario no existe");
		}
	}

}

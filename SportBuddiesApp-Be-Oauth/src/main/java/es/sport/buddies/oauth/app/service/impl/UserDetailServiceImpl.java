package es.sport.buddies.oauth.app.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
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

import es.sport.buddies.entity.app.dto.UsernameAuthenticationDto;
import es.sport.buddies.entity.app.dto.UsuarioDto;
import es.sport.buddies.entity.app.models.entity.CodigoVerificacion;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.ICodigoVerificacionService;
import es.sport.buddies.entity.app.models.service.IUsuarioService;
import es.sport.buddies.oauth.app.constantes.ConstantesApp;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailServiceImpl.class);
  
  private IUsuarioService usuarioService;
  
  private ICodigoVerificacionService codigoVerificacionService;
  
  private EmailServiceImpl emailServiceImpl;
  
	// Por defecto no agrega la inyección de las depenencias, para hacerlo, lo inyectamos desde SecurityConfig.java mediante su constructor
	public UserDetailServiceImpl(IUsuarioService usuarioService,ICodigoVerificacionService codigoVerificacionService,
	    EmailServiceImpl emailServiceImpl) {
		this.usuarioService = usuarioService;
		this.codigoVerificacionService = codigoVerificacionService;
		this.emailServiceImpl = emailServiceImpl;
	}
	
	@Override
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
			LOGGER.info("Se ha localizado al usuario exitosamente, se procede almacenarlo en el contexto de SpringSecurity");
			
			UsernameAuthenticationDto userAuthentication = new UsernameAuthenticationDto(usuario,null, authorities);
			UsuarioDto usuDto = new UsuarioDto();
			usuDto.setIdUsuario(usuario.getIdUsuario());
			usuDto.setNombreUsuario(usuario.getNombreUsuario());			
			userAuthentication.setUsuarioDto(usuDto);
						
			SecurityContextHolder.getContext().setAuthentication(userAuthentication);

			CodigoVerificacion codVerif = CodigoVerificacion.builder()
			    .codigo(RandomStringUtils.randomAlphanumeric(8))
			    .tiempoExpiracion(LocalDateTime.now().plusMinutes(5))
          .usuario(Usuario.builder()
              .idUsuario(usuario.getIdUsuario())
              .build())
          .build();
			
			//guardarCodigoVerificacion(codVerif, usuario);
			
			return new User(usuario.getNombreUsuario(),usuario.getPassword(),usuario.getEnabled(),true,true,true,authorities);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Usuario no existe");
		}
	}

	/**
	 * Función encargada de almacenar el token y enviar el correo
	 * @param codVerif
	 * @param usuario
	 * @throws Exception
	 */
	private void guardarCodigoVerificacion(CodigoVerificacion codVerif, Usuario usuario) throws Exception {
	  try {
      CodigoVerificacion cod = codigoVerificacionService.findByUsuario_IdUsuario(usuario.getIdUsuario());
      if (cod == null) {
        LOGGER.info("Se prodece almacenar el codigo de verificacion");
        codigoVerificacionService.guardarCodigoVerificacion(codVerif);
        LOGGER.info("Código almacenado exitosamente");
      } else {
        cod.setCodigo(RandomStringUtils.randomAlphanumeric(8));
        codigoVerificacionService.actualizarTiempoExpiracion(LocalDateTime.now().plusMinutes(5),
            usuario.getIdUsuario(), cod.getCodigo());
      }
      ConstantesApp.CODIGOVERIFICACION = cod != null ? usuario.getIdUsuario()
          : codVerif.getUsuario().getIdUsuario();
      emailServiceImpl.sendEmailCodeVerification(usuario.getEmail(), cod.getCodigo(), usuario.getNombreUsuario());
    } catch (Exception e) {
      throw new Exception(e);
    }
	}
	
}

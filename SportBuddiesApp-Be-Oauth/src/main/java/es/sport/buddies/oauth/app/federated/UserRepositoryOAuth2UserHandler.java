package es.sport.buddies.oauth.app.federated;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IUsuarioService;

/**
 * Clase encargada de validar al usuario recibido desde google, en el caso de
 * que no exista se creara en BBDD
 */
@Service
public class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryOAuth2UserHandler.class);

  private IUsuarioService usuarioService;

  @Autowired
  private BCryptPasswordEncoder bCryptPass;
  
  public UserRepositoryOAuth2UserHandler(IUsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @Override
  public void accept(OAuth2User user) {
    if (this.usuarioService.findByEmail(user.getName()) == null) {
      Usuario usuario = new Usuario();
      usuario.setApellido(user.getAttributes().get("family_name").toString());
      // user.getAttributes().get("given_name").toString()
      usuario.setNombreUsuario(user.getName());
      usuario.setEnabled(true);
      usuario.setEmail(user.getName());
      usuario.setPictureUrl(user.getAttributes().get("picture").toString());
      usuario.setPassword(bCryptPass.encode(generateSecureRandomPassword()));
      this.usuarioService.guardarUsuario(usuario);
    } else {
      LOGGER.info("bienvenido {}", user.getAttributes().get("given_name"));
    }
  }
  
  private String generateSecureRandomPassword() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] randomBytes = new byte[16];
    secureRandom.nextBytes(randomBytes);

    // Convertir los bytes aleatorios a una cadena legible
    return Base64.getUrlEncoder().encodeToString(randomBytes);
  }
  
}

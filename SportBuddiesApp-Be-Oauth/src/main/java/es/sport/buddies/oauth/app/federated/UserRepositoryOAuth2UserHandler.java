package es.sport.buddies.oauth.app.federated;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.models.entity.UsuarioGoogle;
import es.sport.buddies.entity.app.models.service.IUsuarioGoogleService;

/**
 * Clase encargada de validar al usuario recibido desde google, en el caso de que no exista se creara en BBDD
 */
@Service
public class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryOAuth2UserHandler.class);
  
  private IUsuarioGoogleService googleService;
  
  public UserRepositoryOAuth2UserHandler(IUsuarioGoogleService googleService) {
    this.googleService = googleService;
  }

  @Override
  public void accept(OAuth2User user) {
      if (!this.googleService.findByEmail(user.getName()).isPresent()) {
          UsuarioGoogle usuGoogle = new UsuarioGoogle();
          usuGoogle.setEmail(user.getName());
          usuGoogle.setName(user.getAttributes().get("name").toString());
          usuGoogle.setGivenName(user.getAttributes().get("given_name").toString());
          usuGoogle.setFamilyName(user.getAttributes().get("family_name").toString());
          usuGoogle.setPictureUrl(user.getAttributes().get("picture").toString());          
          LOGGER.info("Se procede almacenar al usuario {} logueado desde google", usuGoogle.getEmail());
          this.googleService.guardarUsuarioGoogle(usuGoogle);
      } else {
        LOGGER.info("bienvenido {}", user.getAttributes().get("given_name"));
      }
  }
}

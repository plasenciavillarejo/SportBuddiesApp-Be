package es.sport.buddies.oauth.app.federated;

import java.io.IOException;
import java.util.function.Consumer;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class FederatedIdentityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final AuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();

  private Consumer<OAuth2User> oauth2UserHandler = user -> {};

  private Consumer<OidcUser> oidcUserHandler = user -> this.oauth2UserHandler.accept(user);
  
  private UserRepositoryOAuth2UserHandler ouaht2Handler;
  
  public FederatedIdentityAuthenticationSuccessHandler() {
    // Constructor vacío
  }
  
  /**
   * Constructor para inyectar el servicio UserRepositoryOAuth2UserHandler para realizar el login cuando recibo un usuario 
   * @param repositoryOAuth2UserHandler
   */
  public FederatedIdentityAuthenticationSuccessHandler(UserRepositoryOAuth2UserHandler repositoryOAuth2UserHandler) {
    this.ouaht2Handler = repositoryOAuth2UserHandler;
  }
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    if (authentication instanceof OAuth2AuthenticationToken) {
      if ((OidcUser) authentication.getPrincipal() instanceof OidcUser) {
        this.oidcUserHandler.accept((OidcUser) authentication.getPrincipal());
        ouaht2Handler.accept((OidcUser) authentication.getPrincipal());
      } else if ((OAuth2User) authentication.getPrincipal() instanceof OAuth2User) {
        this.oauth2UserHandler.accept((OAuth2User) authentication.getPrincipal());
      }
    }
    this.delegate.onAuthenticationSuccess(request, response, authentication);
  }

  public void setOAuth2UserHandler(Consumer<OAuth2User> oauth2UserHandler) {
    this.oauth2UserHandler = oauth2UserHandler;
  }

  public void setOidcUserHandler(Consumer<OidcUser> oidcUserHandler) {
    this.oidcUserHandler = oidcUserHandler;
  }

}

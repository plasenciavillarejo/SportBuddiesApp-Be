package es.sport.buddies.oauth.app.federated;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import es.sport.buddies.entity.app.dto.UsernameAuthenticationDto;

/**
 * Está funcíon se encarga agregar más claims al token antes de generarlo
 */

public class FederatedIdentityIdTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

  @Override
  public void customize(JwtEncodingContext context) {
    if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
      Map<String, Object> thirdPartyClaims = extractClaims(context.getPrincipal());
    
    }
  }

  private Map<String, Object> extractClaims(Authentication principal) {
    Map<String, Object> claims;
    if ((OidcUser) principal.getPrincipal() instanceof OidcUser) {
      OidcUser oidcUser = (OidcUser) principal.getPrincipal();
      OidcIdToken idToken = oidcUser.getIdToken();
      claims = idToken.getClaims();
    } else if ((OAuth2User) principal.getPrincipal() instanceof OAuth2User) {
      OAuth2User oauth2User = (OAuth2User) principal.getPrincipal();
      claims = oauth2User.getAttributes();
    } else {
      claims = Collections.emptyMap();
    }

    return new HashMap<>(claims);
  }
  
}

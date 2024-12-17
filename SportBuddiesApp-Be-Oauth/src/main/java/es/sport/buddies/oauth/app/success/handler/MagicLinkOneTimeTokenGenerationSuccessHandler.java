package es.sport.buddies.oauth.app.success.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MagicLinkOneTimeTokenGenerationSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

  private final Logger LOGGER = LoggerFactory.getLogger(MagicLinkOneTimeTokenGenerationSuccessHandler.class);
  
/*  private final OneTimeTokenGenerationSuccessHandler redirectHandler = new RedirectOneTimeTokenGenerationSuccessHandler(
      "/ott/sent");
*/
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken)
      throws IOException, ServletException {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
        .replacePath(request.getContextPath()).replaceQuery(null).fragment(null).path("/login/ott")
        .queryParam("token", oneTimeToken.getTokenValue());
    String magicLink = builder.toUriString();
    LOGGER.info("Token generado para validar el inicio de sesión es: {}", oneTimeToken.getTokenValue());
    
    //String email = getUserEmail(oneTimeToken.getUsername());
    //this.mailSender.send(email, "Your Spring Security One Time Token", "Use the following link to sign in into the application: " + magicLink);
    //this.redirectHandler.handle(request, response, oneTimeToken);
  }

}

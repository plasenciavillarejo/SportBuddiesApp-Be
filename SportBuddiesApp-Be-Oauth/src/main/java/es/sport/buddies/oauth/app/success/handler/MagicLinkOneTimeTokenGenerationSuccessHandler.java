package es.sport.buddies.oauth.app.success.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IUsuarioService;
import es.sport.buddies.oauth.app.service.impl.EmailServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MagicLinkOneTimeTokenGenerationSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(MagicLinkOneTimeTokenGenerationSuccessHandler.class);
  
  private final OneTimeTokenGenerationSuccessHandler redirectHandler = new RedirectOneTimeTokenGenerationSuccessHandler(
      "/login/generate-token");

  @Autowired
  private IUsuarioService usuarioService;
  
  @Autowired
  private EmailServiceImpl emailServiceImpl;
  
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken)
      throws IOException, ServletException {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
        .replacePath(request.getContextPath()).replaceQuery(null).fragment(null).path("/login/validate-token")
        .queryParam("token", oneTimeToken.getTokenValue());

    Usuario usuario = usuarioService.findByNombreUsuario(oneTimeToken.getUsername());

    if (usuario == null) {
      usuario = usuarioService.findByEmail(oneTimeToken.getUsername());
    }

    if (usuario == null) {
      response.setStatus(HttpServletResponse.SC_FOUND); // Código de estado 302 para redirección
      response.setHeader("Location", "/login?error");
      response.flushBuffer();
    } else {
      LOGGER.info("Token generado para validar el inicio de sesión es: {}", builder.toUriString());
      emailServiceImpl.sendEmailCodeVerification(usuario.getEmail(), oneTimeToken.getTokenValue(),
          usuario.getNombreUsuario());
    }
    this.redirectHandler.handle(request, response, oneTimeToken);
  }

}

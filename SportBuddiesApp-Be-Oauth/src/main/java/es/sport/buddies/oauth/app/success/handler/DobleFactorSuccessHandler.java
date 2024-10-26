package es.sport.buddies.oauth.app.success.handler;

import java.io.IOException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import es.sport.buddies.oauth.app.constantes.ConstantesApp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DobleFactorSuccessHandler implements AuthenticationSuccessHandler {

  private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
  
  private final Authentication authToken = new AnonymousAuthenticationToken("anonymous",
      "anonymous", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS", "ROLE_TWO_F"));
  
  private final AuthenticationSuccessHandler authenticationSuccessHandler;
    
  public DobleFactorSuccessHandler() {
    SimpleUrlAuthenticationSuccessHandler urlAuthSuccesHandler = new SimpleUrlAuthenticationSuccessHandler("/dobleFactor");
    urlAuthSuccesHandler.setAlwaysUseDefaultTargetUrl(true);
    this.authenticationSuccessHandler = urlAuthSuccesHandler;
  }
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    ConstantesApp.AUTHENTICATION = authentication;
    saveAuthentication(request, response);
    this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authToken);
    
  }

  private void saveAuthentication(HttpServletRequest request, HttpServletResponse response) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authToken);
    SecurityContextHolder.setContext(context);
    securityContextRepository.saveContext(context, request, response);
  }
  
  
}

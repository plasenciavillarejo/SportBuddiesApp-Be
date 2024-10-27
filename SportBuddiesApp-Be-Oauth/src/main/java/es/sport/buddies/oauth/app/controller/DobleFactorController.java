package es.sport.buddies.oauth.app.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.sport.buddies.entity.app.models.entity.CodigoVerificacion;
import es.sport.buddies.entity.app.models.service.ICodigoVerificacionService;
import es.sport.buddies.oauth.app.constantes.ConstantesApp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class DobleFactorController {

  private static final Logger LOGGER = LoggerFactory.getLogger(DobleFactorController.class);
  
  private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
  
  private final AuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler(
      "/dobleFactor?error");

  @Autowired
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  @Autowired
  private ICodigoVerificacionService codigoVerificacionService;
  
  @GetMapping("/dobleFactor")
  public String twofactor(@RequestParam(name = "error", required = false) String error, Model model) throws Exception {        
    if(error != null) {
      model.addAttribute("error", error);
    }
    return "dobleFactor";
  }

  @PostMapping("/dobleFactor")
  public void validateCode(@RequestParam("code") String code, HttpServletRequest req, HttpServletResponse res,
      Model model)
      throws ServletException, IOException {
    CodigoVerificacion cod = codigoVerificacionService.findByUsuario_IdUsuario(ConstantesApp.CODIGOVERIFICACION);
    if(code.equals(cod.getCodigo()) && cod.getTiempoExpiracion().isAfter(LocalDateTime.now())) {
      this.authenticationSuccessHandler.onAuthenticationSuccess(req, res, getAuthentication(req, res));
    } else {
      String errorMsg = cod.getTiempoExpiracion().isBefore(LocalDateTime.now())
          ? "La fecha del token ha expirado, por favor, cierre el navegador vuelva a iniciar sesión" 
              : "El código es erróneo, por favor, vuelva a intentarlo";
      //authenticationFailureHandler.onAuthenticationFailure(req, res, new BadCredentialsException("invalid code"));
      // Redirigir a la página de doble factor con el mensaje de error
      res.sendRedirect("/dobleFactor?error=" + URLEncoder.encode(errorMsg, "UTF-8"));
    }
  }

  private Authentication getAuthentication(HttpServletRequest req, HttpServletResponse res) {
    Authentication authentication = ConstantesApp.AUTHENTICATION;
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(authentication);
    SecurityContextHolder.setContext(securityContext);
    securityContextRepository.saveContext(securityContext, req, res);
    return authentication;
  }

  
}

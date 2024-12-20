package es.sport.buddies.oauth.app.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.sport.buddies.oauth.app.constantes.ConstantesApp;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    
  @GetMapping(value = "/login")
  public String login(Model model) {
    model.addAttribute("aplicacionFront", ConstantesApp.APLICACIONANGULAR.concat("/nuevo-usuario"));
    model.addAttribute("loginPasskey", ConstantesApp.APLICACIONANGULAR.concat("/login-passkey"));
    return "login";
  }
  
  /**
   * Habilita la vista customizada en la que debemos de insertar el One-Time token
   * @return
   */
  @GetMapping(value = "/login/generate-token")
  public String loginOneToken() {
    return "login-ott";
  }
  
  /**
   * Servicio encargado de redireccionar la vista de angular una vez que se ha validado el token generado.
   * @param token
   * @param response
   * @throws IOException
   */
  @PostMapping(value = "/login/validate-token")
  public void loginOneTimeTokenPost(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
    response.setStatus(HttpServletResponse.SC_FOUND);  // Código de estado 302 para redirección
    response.setHeader("Location", ConstantesApp.APLICACIONANGULAR);
    response.flushBuffer(); 
  }
  
}

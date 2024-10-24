package es.sport.buddies.oauth.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.sport.buddies.oauth.app.constantes.ConstantesApp;

@Controller
public class LoginController {
  
  @GetMapping(value = "/login")
  public String login(Model model) {
    model.addAttribute("aplicacionFront", ConstantesApp.APLICACIONANGULAR.concat("/nuevo-usuario"));
    return "login";
  }
  
}

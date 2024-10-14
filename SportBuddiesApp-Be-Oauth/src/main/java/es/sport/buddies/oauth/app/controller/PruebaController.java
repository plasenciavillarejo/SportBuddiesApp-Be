package es.sport.buddies.oauth.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PruebaController {

  @GetMapping(value = "/prueba")
  public String prueba() {
    return "prueba";
  }
}

package es.sport.buddies.main.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class CookieController {
   
 @Autowired
 private HttpServletResponse response;
 
 /**
  * Función encargada de borrar la cookie cuando ha expirado el token. De está forma cuando entra para hacer el login no matiene al usuario anterior guardado
  * @return
  */
  @GetMapping(value = "/borrarCookie")
  public ResponseEntity<Void>  borrarCookie() {
    Cookie c = new Cookie("JSESSIONID", "");
    c.setMaxAge(0);
    c.setPath("/");
    c.setHttpOnly(true);
    //c.setSecure(true);
    response.addCookie(c);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  
}

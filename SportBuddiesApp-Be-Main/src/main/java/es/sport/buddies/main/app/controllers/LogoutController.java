package es.sport.buddies.main.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LogoutController {
   
 @Autowired
 private HttpServletResponse response;
 
  @GetMapping(value = "/borrarCookie")
  private ResponseEntity<Void>  borrarCookie() throws Exception {
    Cookie c = new Cookie("JSESSIONID", "");
    c.setMaxAge(0);
    c.setPath("/");
    c.setHttpOnly(true);
    c.setSecure(true) ;
    response.addCookie(c);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  
}

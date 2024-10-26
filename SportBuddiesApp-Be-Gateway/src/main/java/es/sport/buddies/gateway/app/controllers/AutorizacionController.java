package es.sport.buddies.gateway.app.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AutorizacionController {

  /**
   * Función encargada de obtener el código de autorizacion, recibe un code que es
   * como se llama en el servidor de autorizacion. Nos permite intercambiar el
   * código de autorizacion por un token para que podamos acceder a las rutas
   * protegidas.
   * 
   * @param code
   * @return CODE
   */
  @GetMapping(value = "/authorized")
  public ResponseEntity<Map<String, String>> autorizacionOauth(@RequestParam String code) {
    return new ResponseEntity<>(Collections.singletonMap("code", code), HttpStatus.OK);
  }

  @GetMapping(value = "/logout")
  public void logout(HttpRequest request) {
    if (request.getHeaders().get(HttpHeaders.AUTHORIZATION) != null) {
      SecurityContextHolder.clearContext();
    }
  }

  @GetMapping(value = "/cerrarSesion")
  public ResponseEntity<Void> logout(ServerHttpRequest request, ServerHttpResponse response) {
    if (request.getHeaders().get(HttpHeaders.AUTHORIZATION) != null) {
      SecurityContextHolder.clearContext();
      ResponseCookie res = ResponseCookie.from("JSESSIONID", "")
          .path("/") // Aseguramos que la ruta sea la misma que la de la cookie
          .maxAge(0) // Establece la edad máxima de la cookie a 0 para eliminarla
          .httpOnly(true) // Aseguramos que sea httpOnly si es necesario
          .secure(true) // Aseguramos que sea segura si usamos HTTPS
          .sameSite("Lax") // Define el comportamiento de SameSite si es necesario
          .build();
      response.getCookies().set("JSESSIONID", res);
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autorizado");
    }
    // Retornar un código 200 sin cuerpo
    return new ResponseEntity<>(HttpStatus.OK);
  }

}

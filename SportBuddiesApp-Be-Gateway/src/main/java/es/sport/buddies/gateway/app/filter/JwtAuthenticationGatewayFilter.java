/*package es.sport.buddies.gateway.app.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import es.sport.buddies.gateway.app.utilidades.Utilidades;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationGatewayFilter implements WebFilter {
  
  @Autowired
  private final JwtDecoder jwtDecoder;

  public JwtAuthenticationGatewayFilter(JwtDecoder jwtDecoder) {
      this.jwtDecoder = jwtDecoder;
  }

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuerUriKeyCloack;
  
  /**
   * Filtro encargado de validar que el usuario contiene un token, pero no se debe restringirsu acceso ya que el usuario puede consultar algunos servicios inicialmente que no se
   * requieren de ningún token, como puede ser e listado inicial de las provincias, municipios, etc...
  
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
      
    return chain.filter(exchange);
    
    String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

      if(isRutaPublica(exchange.getRequest().getURI().getPath())) {
        return chain.filter(exchange);
      }
      
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
          exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
          return exchange.getResponse().setComplete();
      }
      
      String token = authHeader.substring(7);
      
      try {
          Jwt jwt = jwtDecoder.decode(token);
          // Opcional: Verifica claims específicos, como roles
          if (!isTokenValid(jwt)) {
            // Por ahora la logica de la validacion keycloack se deja comentada para que no de error.
            
            //  exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            //  return exchange.getResponse().setComplete();
          }
      } catch (JwtException e) {
          exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
          return exchange.getResponse().setComplete();
      }

      return chain.filter(exchange);
  }

  /**
   * Función encargada de verificar roles, scope, iss, etc...
   * 
   * @param jwt
   * @return
   
  private boolean isTokenValid(Jwt jwt) {
    return jwt.getClaimAsString("iss").equals(issuerUriKeyCloack);
  }

  /**
   * Verifica si la ruta es pública o no.
   * 
   * @param path La ruta solicitada.
   * @return true si la ruta es pública, false si requiere autenticación.
  
  private boolean isRutaPublica(String path) {
    return Utilidades.publicRoutes.stream().anyMatch(path::startsWith);
  }

}
   */
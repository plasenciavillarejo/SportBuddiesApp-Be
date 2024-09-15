package es.sport.buddies.main.app.filter;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    @Autowired
    private final JwtDecoder jwtDecoder;

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
      String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

      if (authHeader != null && authHeader.startsWith("Bearer ")) {
          String token = authHeader.substring(7);
          LOGGER.info("Token JWT: {}", token);

          try {
              Jwt jwt = jwtDecoder.decode(token);
              String userId = jwt.getClaim("sub");

              List<GrantedAuthority> roles = jwt.getClaimAsStringList("roles").stream().map(role -> (GrantedAuthority) 
                  new SimpleGrantedAuthority(role)).toList();
              
              // Crea un objeto Authentication (puedes usar una clase personalizada o una de Spring Security)
              UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,roles);
              
              // Configura el SecurityContextHolder con la Authentication
              SecurityContextHolder.getContext().setAuthentication(authentication);

              // Puedes usar la Authentication en el filtro o en el backend
          } catch (JwtException e) {
              LOGGER.error("Error decodificando el JWT: {}", e.getMessage());
          }
      }
      filterChain.doFilter(request, response);
    }
}

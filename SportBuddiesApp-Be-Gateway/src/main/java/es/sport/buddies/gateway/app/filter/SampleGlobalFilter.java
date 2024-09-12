package es.sport.buddies.gateway.app.filter;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SampleGlobalFilter implements GlobalFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleGlobalFilter.class);
    
    @Autowired
    private final JwtDecoder jwtDecoder;

    public SampleGlobalFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.info("Ejecutando el filtro PRE Response");

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            LOGGER.info("Token JWT: {}", token);

            try {
                Jwt jwt = jwtDecoder.decode(token);
                String userId = jwt.getClaim("sub"); // Suponiendo que "sub" es el claim que contiene el ID del usuario

                // Crea un objeto Authentication (puedes usar una clase personalizada o una de Spring Security)
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,
                		Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
                
                // Configura el SecurityContextHolder con la Authentication
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Puedes usar la Authentication en el filtro o en el backend
            } catch (JwtException e) {
                LOGGER.error("Error decodificando el JWT: {}", e.getMessage());
            }
        } else {
            LOGGER.info("No se encontró el encabezado Authorization o no contiene un token Bearer");
        }

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            LOGGER.info("Ejecutando el filtro POST Response");
        }));
    }
}
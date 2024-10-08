package es.sport.buddies.gateway.app.security.config;

// Importamos método estático withDefaults
import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import reactor.core.publisher.Mono;

/**
 * Configuracion de forma reactiva (WebFlux) que esta incluida con la depenencia de Spring-Cloud
 */
@Configuration
public class SecurityConfig {

  /**
   * Configuración Cors para la comunicación Entre Front y Back
   * @return
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    corsConfig.setAllowedOriginPatterns(Arrays.asList("*"));
    corsConfig.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
    corsConfig.setAllowCredentials(true);
    corsConfig.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION,HttpHeaders.CONTENT_TYPE, HttpHeaders.CONTENT_DISPOSITION));
    
    // Configuración de las cabeceras CORS
    corsConfig.addExposedHeader(HttpHeaders.AUTHORIZATION);
    corsConfig.addExposedHeader(HttpHeaders.CONTENT_DISPOSITION);
    
    // Pasamos el corsConfig a nuestras rutas urls    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // se aplique a todas nuestras rutas
    source.registerCorsConfiguration("/**", corsConfig);
    
    return source;
  }

	@Bean
	SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
		return http.authorizeExchange(authHttp -> 
			authHttp.pathMatchers("/authorized","/logout",
			    "/api/main/reservaActividad/listarReserva",
			    "/api/main/reservaActividad/comboInicio",
			    "/api/main/reservaActividad/listadoMunicipios",
			    "/api/main/reservaActividad/listadoReserva",
			    "/api/main/borrarCookie").permitAll()
			.pathMatchers("/api/main/listar").hasAnyRole("ADMIN", "USER")
			//.hasAnyAuthority("SCOPE_read", "SCOPE_write") -> De está format trabaja los roles de Oauth 2
			//.pathMatchers(HttpMethod.POST, "/crear").hasAuthority("SCOPE_write")
					.anyExchange().authenticated()
		).csrf(csrf -> csrf.disable())
		    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
				// Deshabilita las sesiones en WebFlux - Con esto evitamos que al acceder a endpoint que no tenemos permisos (403) nos devuelva por defecto el code: "xxx"
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				// Login de ouath2
				.oauth2Login(withDefaults())
				// Cliente
				.oauth2Client(withDefaults())
				// Obtenemos los roles del token que hemos agregador por defecto de Spring Security en vez e utilzar de SCOP_write, SCOPE_read de Oauth2
				.oauth2ResourceServer(resourceServer -> resourceServer.jwt(jwt ->
				// Implementamos al vuelo la clase anónima 'Converter()' para agregar los roles incluidos en SecurityConfig.class dentro de (SportBuildesApp-Be-Oauth)
				jwt.jwtAuthenticationConverter(source -> {
				    Collection<String> roles = source.getClaimAsStringList("roles");
				    Collection<GrantedAuthority> authorities = roles.stream().map(role -> (GrantedAuthority) 
				    		new SimpleGrantedAuthority(role)).toList();
				    return Mono.just(new JwtAuthenticationToken(source, authorities));
				})
				)).build();
	}
	
}

package es.sport.buddies.gateway.app.config;

// Importamos método estático withDefaults
import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import reactor.core.publisher.Mono;

/**
 * Configuracion de forma reactiva (WebFlux) que esta incluida con la depenencia de Spring-Cloud
 */
@Configuration
public class SecurityConfig {

	@Bean
	SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
		return http.authorizeExchange(authHttp -> {
			authHttp.pathMatchers("/authorized", "/logout").permitAll()
			.pathMatchers(HttpMethod.GET, "/api/main/listar").hasAnyRole("ADMIN", "USER")
			//.hasAnyAuthority("SCOPE_read", "SCOPE_write") -> De está format trabaja los roles de Oauth 2
			//.pathMatchers(HttpMethod.POST, "/crear").hasAuthority("SCOPE_write")
					.anyExchange().authenticated();
		}).cors(csrf -> csrf.disable())
				// Deshabilita las sesiones en WebFlux - Con esto evitamos que al acceder a endpoint que no tenemos permisos (403) nos devuelva por defecto el code: "xxx"
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				// Login de ouath2
				.oauth2Login(withDefaults())
				// Cliente
				.oauth2Client(withDefaults())
				// Obtenemos los roles del token que hemos agregador por defecto de Spring Security en vez e utilzar de SCOP_write, SCOPE_read de Oauth2
				.oauth2ResourceServer(resourceServer -> resourceServer.jwt(jwt ->
				// Implementamos al vuelo la clase anónima 'Converter()' para agregar los roles incluidos en SecurityConfig.class dentro de (SportBuildesApp-Be-Oauth)
				
				/* Está clase al vuelo, la he optimizado más abajo
				jwt.jwtAuthenticationConverter(new Converter<Jwt, Mono<AbstractAuthenticationToken>>() {
					@Override
					public Mono<AbstractAuthenticationToken> convert(Jwt source) {
						Collection<String> roles = source.getClaimAsStringList("roles");
						Collection<GrantedAuthority> authorities = roles.stream().map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role)).toList();
						// Devolvemos un objeto mono reactivo pasndo el token y los authorities. Ahora ya podremos loguearnos con los roles de Spring Security
						return Mono.just(new JwtAuthenticationToken(source, authorities));
				}})
				*/
				jwt.jwtAuthenticationConverter(source -> {
				    Collection<String> roles = source.getClaimAsStringList("roles");
				    Collection<GrantedAuthority> authorities = roles.stream().map(role -> (GrantedAuthority) 
				    		new SimpleGrantedAuthority(role)).toList();
				    return Mono.just(new JwtAuthenticationToken(source, authorities));
				})
				))
				.build();
	}
	
}

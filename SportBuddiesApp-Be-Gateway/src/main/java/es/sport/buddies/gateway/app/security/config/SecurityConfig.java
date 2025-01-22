package es.sport.buddies.gateway.app.security.config;

// Importamos método estático withDefaults
import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import es.sport.buddies.entity.app.models.entity.ClientesOauth;
import es.sport.buddies.entity.app.models.service.IClientesOauthService;
import es.sport.buddies.gateway.app.constantes.ConstantesGateway;
import es.sport.buddies.gateway.app.utilidades.Utilidades;
import reactor.core.publisher.Mono;

/**
 * Configuracion de forma reactiva (WebFlux) que esta incluida con la depenencia de Spring-Cloud
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  
  @Value("${port.oauth}")
  private String portOauth;
  
  /**
   * Configuración Cors para la comunicación Entre Front y Back
   * @return
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    // PLASENCIA - CORREGIR LUEGO
    corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://200.234.230.76:4200"));
    corsConfig.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
    corsConfig.setAllowCredentials(true);
    corsConfig.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION,HttpHeaders.CONTENT_TYPE, HttpHeaders.CONTENT_DISPOSITION));
    
    // Pasamos el corsConfig a nuestras rutas urls    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // se aplique a todas nuestras rutas
    source.registerCorsConfiguration("/**", corsConfig);
    
    return source;
  }

	@Bean
	SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, Utilidades utilidades) {
		return http.authorizeExchange(authHttp -> 
			authHttp.pathMatchers(utilidades.publicRoutes.toArray(new String[0])).permitAll()
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
				// Registramos los clientes que existen en la BBDD.
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
	
	/* Los métodos clientRegistrationRepository() y authorizedClientManager() sustituyen a la creación de los clientes inyectados a mano en el application.yml	 */
	/**
	 * FUnción encargada de agregar los clientes 
	 * @return
	 */
  @Bean
  ReactiveClientRegistrationRepository clientRegistrationRepository(IClientesOauthService clientOauthService) {
    List<ClientesOauth> clientRegis = clientOauthService.findAll();

    List<ClientRegistration> listClient = clientRegis.stream()
        .map(cli -> ClientRegistration.withRegistrationId(cli.getClientName())
            .clientId(cli.getClientId())
            .clientSecret(cli.getClientSecret())
            .clientName(cli.getClientName())
            .scope(Arrays.asList(cli.getScopes().split(",")))
            .clientAuthenticationMethod(new ClientAuthenticationMethod(cli.getAuthenticationMethods()))
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri(Arrays.stream(cli.getRedirectUris().split(","))
                .filter(uri -> uri.contains("authorized") || uri.contains("authorize"))
                .findFirst()
                .orElse("http://default-redirect-uri.com"))
            .authorizationUri(ConstantesGateway.APPSPORTBUDDIOAUTH.concat("/oauth2/authorize"))
            .tokenUri(ConstantesGateway.APPSPORTBUDDIOAUTH.concat("/oauth2/token"))
            .jwkSetUri(ConstantesGateway.APPSPORTBUDDIOAUTH.concat("/.well-known/jwks.json"))
            .build())
       /* .peek(lo -> System.out.println("\n" + ConstantesGateway.APPSPORTBUDDIOAUTH + "\n" 
            + "http://".concat(System.getenv("IP_HOST")).concat(":"+portOauth).concat("/oauth2/authorize")))*/
        .toList();

    // Creamos repositorio reactivo
    return registrationId -> Mono.justOrEmpty(listClient.stream()
        .filter(clientRegistration -> clientRegistration.getRegistrationId().equals(registrationId))
        .findFirst());
  }
	
  @Bean
  ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
      ReactiveClientRegistrationRepository clientRegistrationRepository,
      ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {

    ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider = ReactiveOAuth2AuthorizedClientProviderBuilder
        .builder().authorizationCode().refreshToken().clientCredentials().build();

    DefaultReactiveOAuth2AuthorizedClientManager authorizedClientManager = new DefaultReactiveOAuth2AuthorizedClientManager(
        clientRegistrationRepository, authorizedClientRepository);

    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

    return authorizedClientManager;
  }

  
}

package es.sport.buddies.oauth.app.security.config;

import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.ICodigoVerificacionService;
import es.sport.buddies.entity.app.models.service.IUsuarioService;
import es.sport.buddies.oauth.app.constantes.ConstantesApp;
import es.sport.buddies.oauth.app.denied.handler.CustomAccessDeniedHandler;
import es.sport.buddies.oauth.app.federated.FederatedIdentityAuthenticationSuccessHandler;
import es.sport.buddies.oauth.app.federated.UserRepositoryOAuth2UserHandler;
import es.sport.buddies.oauth.app.service.impl.UserDetailServiceImpl;
import es.sport.buddies.oauth.app.success.handler.DobleFactorSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  
  @Autowired
  private UserRepositoryOAuth2UserHandler ouaht2Handler;
  
  /**
   * Si queremos que la validación de la seguridad se haga desde nuestra BBDD deberemos de inyectar UserDetailService.java de Spring security y dentro del constructor
   * enviar el  IUsuarioService.java, de lo contrario dentro de dicha clase no se tiene acceso a la inyección de las dependencias
   */
  @Autowired
  private IUsuarioService usuarioService;
  
  @Autowired
  private ICodigoVerificacionService codigoVerificacionService;
  
  public UserDetailServiceImpl usu() {
    return new UserDetailServiceImpl(usuarioService,codigoVerificacionService);
  }
  
  private AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new FederatedIdentityAuthenticationSuccessHandler(ouaht2Handler);
  }
  
  @Bean
  static BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AccessDeniedHandler accessDeniedHandler() {
      return new CustomAccessDeniedHandler();
  }
    
  @Bean
  AuthenticationSuccessHandler authSuccesHandler() {
    return new SavedRequestAwareAuthenticationSuccessHandler();
  }
  
  @Bean
  @Order(1)
  SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults());
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

    // Enable OpenID Connect 1.0
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
    //.authorizationEndpoint(auth -> auth.consentPage(ConstantesApp.CUSTOMCONSENTPAGE))
    .oidc(Customizer.withDefaults());
        
    http.exceptionHandling(exc -> exc.accessDeniedHandler(accessDeniedHandler()));
    
    //  Redirigir a la página de inicio de sesión cuando no está autenticado desde el punto final de autorización
    http.exceptionHandling(exceptions -> exceptions.defaultAuthenticationEntryPointFor(
            new LoginUrlAuthenticationEntryPoint(ConstantesApp.LOGIN),
            new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
        // Aceptar tokens de acceso para información de usuario y/o registro de cliente
        .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowCredentials(true);
    corsConfig.setAllowedOriginPatterns(Arrays.asList("*"));
    corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Métodos permitidos
    corsConfig.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_DISPOSITION, HttpHeaders.CONTENT_TYPE,
        HttpHeaders.ACCEPT, HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));  // Headers permitidos
    // Configuración de las cabeceras CORS
    corsConfig.addExposedHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
    corsConfig.addExposedHeader(HttpHeaders.AUTHORIZATION);
    corsConfig.addExposedHeader(HttpHeaders.CONTENT_DISPOSITION);
    
    // Pasamos el corsConfig a nuestras rutas urls    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // se aplique a todas nuestras rutas
    source.registerCorsConfiguration("/**", corsConfig);
    
    return source;
  }
  
  // Configuración para el Default Security Filter Chain
  @Bean
  @Order(2)
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    /* Cuando implemento un formulario propio, al loguear por google, la repuesta llega a '/error?continue', para poder enviar el code generado al servicio angular tengo que permitir
     * dicho endpoint dentro de mi seguridad. Posteriormente ya funcionaría correctamente el redirect hacia el servicio de angular para generar el token.
     */
    http.authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/login","/error/**","/img/**", "/css/**",
            "/assets/**", "/clienteOauth/**").permitAll()
        .requestMatchers("/dobleFactor").hasAnyAuthority("ROLE_TWO_F")
        .anyRequest().authenticated())
        .formLogin(form -> form.loginPage(ConstantesApp.LOGIN)
            .successHandler(new DobleFactorSuccessHandler())
            .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"))
            )
        .oauth2Login(oauth -> oauth.loginPage(ConstantesApp.LOGIN)
            .successHandler(authenticationSuccessHandler()))
        .logout(logout -> logout.logoutSuccessUrl(ConstantesApp.LOGOUTANGULAR))
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .exceptionHandling(exc -> exc.accessDeniedHandler(accessDeniedHandler()));
    return http.build();
  }
  
  /**
   * Función para trabajar con usuarios en memoria para hacer pruebas
   * @return
   
  @Bean
  UserDetailsService userDetailsService() {
    UserDetails userDetails = User.builder()
        .username("jose")
        .password("{noop}12345")
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(userDetails);
  }
   */
  
  /* Configuración de nuestro cliente FRONT-END
	 Para acceder a más informacíon de oauthg acceder al siguiente EndPoint: http://localhost:9000/.well-known/oauth-authorization-server 
  @Bean
  RegisteredClientRepository registeredClientRepository() {
    RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId(ConstantesApp.CLIENTID)
        .clientSecret(passwordEncoder().encode(ConstantesApp.CLIENTSECRET))
        //.clientSecret("{noop}12345")
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        // Forma de autorización tipicas utilizado el estandar OUATH2
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        // Protegemos el proyecto principal (SPRING CLOUD GATEWAY) - Por defecto, esto no hace falta implementarlo ya que viene
        .redirectUri(ConstantesApp.REDIRECTURIOAUTH2)
        // Deberemos de crear un endpoint dentro del cliente (SPRING CLOUD GATEWAY) para poder obtener el código de AUTHORIZATION_CODE para generar el token
        .redirectUri(ConstantesApp.ENDPOINTAUTHORIZATION)
        // Ruta por defecto cuando se haga logout dentro de la web ( Es opcional cuando trabajamos con servicios REST)
        .postLogoutRedirectUri(ConstantesApp.ENDPOINTLOGOUT)
        // Roles para la aplicación por defecto
        .scope(OidcScopes.OPENID)
        .scope(OidcScopes.PROFILE)
        //.scope("read")
        //.scope("write")
        // Modificaicón del tiempo de expiración del Token y Refresh Token
        .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(12))
        		.refreshTokenTimeToLive(Duration.ofDays(1)).build())
        // requireAuthorizationConsent(false) se indica a false ya que por defecto los roles son OPENID y PROFILE
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build()).build();
    
    RegisteredClient oauthDebugger = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId("clietn-oauthdebugger")
        .clientSecret(passwordEncoder().encode("secret"))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .redirectUri("https://oauthdebugger.com/debug")
        .scope(OidcScopes.OPENID)
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build()).build();
    
    RegisteredClient clientAngular = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId(ConstantesApp.CLIENTIDANGULAR)
        .clientSecret(passwordEncoder().encode(ConstantesApp.CLIENTSECRETANGULAR))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        // Envia el CODE a la página de angular
        .redirectUri(ConstantesApp.REDIRECTANGULAR)
        .scope(OidcScopes.OPENID)
        .scope(OidcScopes.PROFILE)
        .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(12))
            .refreshTokenTimeToLive(Duration.ofDays(1)).build())
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()).build();
    
    return new InMemoryRegisteredClientRepository(oidcClient,clientAngular);
  }
*/
  @Bean
  JWKSource<SecurityContext> jwkSource() {
    KeyPair keyPair = generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    exportPublicKey(publicKey);
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    // Está llave va a quedar registrada en el BE
    RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  private static KeyPair generateRsaKey() {
    KeyPair keyPair;
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ConstantesApp.RSA);
      keyPairGenerator.initialize(2048);
      keyPair = keyPairGenerator.generateKeyPair();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
    return keyPair;
  }

  /**
   * Función encargada de generar la llave publica para poder acceder al token, de lo contrario, si se pretende desencriptar el token
   * no se podrá, esto se hace para poder trabajar en el gateway y meter en el contexto de spring al usuario.
   * En cada ejecución creara el fichero desde cero, evitando la duplicida de la información
   * @param publicKey
   */
  private static void exportPublicKey(RSAPublicKey publicKey) {
    try (FileOutputStream fos = new FileOutputStream(ConstantesApp.FICHERPEMPLUBLIKEY)) {
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey.getEncoded());
      KeyFactory keyFactory = KeyFactory.getInstance(ConstantesApp.RSA);
      PublicKey pubKey = keyFactory.generatePublic(keySpec);
      fos.write(pubKey.getEncoded());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
	
  @Bean
  JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  @Bean
  AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().build();
  }
 
  /**
   * Lo mismo que OAuth2TokenCustomizer pero crea una clase en medio
   * @return   
  @Bean
  OAuth2TokenCustomizer<JwtEncodingContext> idTokenCustomizer() {
      return new FederatedIdentityIdTokenCustomizer();
  }
  */
  
  /**
   * Función encargada de agregar información al token que por defecto no se agrega
   * @return
   */
  @Bean
  OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
    return context -> {
      if (context.getTokenType().getValue().equalsIgnoreCase(OAuth2TokenType.ACCESS_TOKEN.getValue())) {
        Authentication principal = context.getPrincipal();
        Usuario usu = usuarioService.findByNombreUsuario(principal.getName());
        if(usu == null) {
          usu = usuarioService.findByEmail(principal.getName());
        }
        context.getClaims()
            .claim("roles", principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
            .claim("idusuario", usu.getIdUsuario());
      }
    };
  }
  
  
}
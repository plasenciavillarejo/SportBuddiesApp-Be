package es.sport.buddies.oauth.app.security.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import es.sport.buddies.entity.app.models.entity.ClientesOauth;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IClientesOauthService;
import es.sport.buddies.entity.app.models.service.ICodigoVerificacionService;
import es.sport.buddies.entity.app.models.service.IUsuarioService;
import es.sport.buddies.oauth.app.constantes.ConstantesApp;
import es.sport.buddies.oauth.app.denied.handler.CustomAccessDeniedHandler;
import es.sport.buddies.oauth.app.federated.FederatedIdentityAuthenticationSuccessHandler;
import es.sport.buddies.oauth.app.federated.UserRepositoryOAuth2UserHandler;
import es.sport.buddies.oauth.app.filter.JwtAuthenticationFilter;
import es.sport.buddies.oauth.app.service.impl.EmailServiceImpl;
import es.sport.buddies.oauth.app.service.impl.UserDetailServiceImpl;
import es.sport.buddies.oauth.app.success.handler.MagicLinkOneTimeTokenGenerationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  
  @Autowired
  private UserRepositoryOAuth2UserHandler ouaht2Handler;

  @Value("${server.port}")
  private String portOauth;
  
  /**
   * Si queremos que la validación de la seguridad se haga desde nuestra BBDD deberemos de inyectar UserDetailService.java de Spring security y dentro del constructor
   * enviar el  IUsuarioService.java, de lo contrario dentro de dicha clase no se tiene acceso a la inyección de las dependencias
   */
  @Autowired
  private IUsuarioService usuarioService;
  
  @Autowired
  private ICodigoVerificacionService codigoVerificacionService;
  
  @Autowired
  private EmailServiceImpl emailServiceImpl;
  
  public UserDetailServiceImpl usu() {
    return new UserDetailServiceImpl(usuarioService,codigoVerificacionService,emailServiceImpl);
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
  OAuth2AuthorizationService authorizationService() {
      return new InMemoryOAuth2AuthorizationService(); 
  }
  
  /**
   * Importantes: Necesario para poder trabajar con contenedores docker, en el caso de no hacerlo de está forma, siempre tendremos el problema de
   * "problem gateway Exceeded maxRedirects. Probably stuck in a redirect loop" ya que el contenedor está en una IP y el maquina en otra. Cuando generamos el token
   * ya sea con a nivel interno de contenedor o con un localhost, el ISS que se genera debe ser igual al que genero el endpoint del code:
   * Ejemplo trabajando con el BE:
   *  1.- Genero el Token mediante el endpoint -> http://localhost:9000/oauth2/token
   *  2.- Cuando veo token dentro de jwt.io veo que el ISS lo genera http://localhost:9000
   *  3.- Dentro del Gateway 'application.yml' le indico que el endpoint que va a validar la autenticidad es el http://localhost:900.
   *  Sí esto no coincide tendremos un '401 no autorizado' ya que no confia quien genero el token.
   * @return
   */
  @Bean
  AuthorizationServerSettings authorizationServerSettings() {
    return System.getenv("IP_HOST") != null
        ? AuthorizationServerSettings.builder().issuer(ConstantesApp.HTTP.concat(System.getenv("IP_HOST")).concat(":" + portOauth)).build()
        : AuthorizationServerSettings.builder().build();
  }
  
  @Bean
  @Order(1)
  SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    // Enable OpenID Connect 1.0
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
        OAuth2AuthorizationServerConfigurer.authorizationServer()
        .oidc(Customizer.withDefaults()); 
    http.cors(Customizer.withDefaults())
        .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
        .with(authorizationServerConfigurer, Customizer.withDefaults())
        .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
         // Redirigir a la página de inicio de sesión cuando no está autenticado desde el punto final de autorización
        .exceptionHandling(exceptions -> exceptions.defaultAuthenticationEntryPointFor(
              new LoginUrlAuthenticationEntryPoint(ConstantesApp.LOGIN),
              new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
        .exceptionHandling(exc -> exc.accessDeniedHandler(accessDeniedHandler()))
        // Aceptar tokens de acceso para información de usuario y/o registro de cliente
        .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()))
        // Configuración para acceder mediante certificado digital
        .x509(cert -> cert.subjectPrincipalRegex("CN=(.*?),")
            .userDetailsService(usu()));
    return http.build();
  }

  /**
   * Función CORS necesaria ya que el FE se comunica para loguear el token de OAUTH2 directamente con la aplicación
   * en el caso de no indicar los cors, no se podrá generar el token cuando se reciba el code
   * @return
   */
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
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, MagicLinkOneTimeTokenGenerationSuccessHandler magic
      , JwtAuthenticationFilter authenticationFilter) throws Exception {
    /* Cuando implemento un formulario propio, al loguear por google, la repuesta llega a '/error?continue', para poder enviar el code generado al servicio angular tengo que permitir
     * dicho endpoint dentro de mi seguridad. Posteriormente ya funcionaría correctamente el redirect hacia el servicio de angular para generar el token.
     * Pasa lo mismo cuando implem  entamos el POST para /login/validate-token, se debe permitir para evitar que se quede sin redireccionar cuando termina
     * el servicio en /login/generate-token/continue?
     */    
    http
    .csrf(csrf -> csrf.disable())
    .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/login/**","/oauth2/**", "/error/**","/img/**", "/css/**",
            "/assets/**", "/clienteOauth/**", "/login/generate-token/**",
            "/webauthn/**","/passkeys/**").permitAll()
        .requestMatchers("/dobleFactor").hasAnyAuthority("ROLE_TWO_F")
        .anyRequest().authenticated())
       //.formLogin(Customizer.withDefaults())
       //.oneTimeTokenLogin(Customizer.withDefaults())
       .formLogin(form -> form.loginPage(ConstantesApp.LOGIN)
            // Para trabajar con el CLIENTE BE debemos comentar el successHandler 
            //.successHandler(new DobleFactorSuccessHandler())
            .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error")))
       /* Configuración seguridad One-Time Token */
        .oneTimeTokenLogin(ott -> ott
            // Por defecto el filtro 'OneTimeTokenGenerationSuccessHandler' captura el PATH '/ott/generate', lo cambio por uno propio
            .tokenGeneratingUrl("/login/filter-generate-token")
            // Por defecto este servicio redirige a /login/ott que proporciona spring, lo cambio por uno propio
            .defaultSubmitPageUrl("/login/generate-token")
            // Para que spring no me proporcione la pagina le indica un false, he implemento yo la mia
            .showDefaultSubmitPage(false)
            // Cuando se envia el token el servicio POST por defecto es /login/ott, lo cambio por uno propio
            .loginProcessingUrl("/login/validate-token"))
        /* Configuración seguridad para PASSKEYS*/
        .webAuthn(webAuth -> webAuth.rpName("Spring Security Passkeys")
            .rpId("SportBuddiesApp")
            .allowedOrigins(System.getenv("IP_HOST") != null
                ? ConstantesApp.HTTP.concat(System.getenv("IP_HOST")).concat(":" + portOauth)
                : "http://localhost:9000"))
        .oauth2Login(oauth -> oauth.loginPage(ConstantesApp.LOGIN)
            .successHandler(authenticationSuccessHandler()))
        .logout(logout -> logout.logoutSuccessUrl(ConstantesApp.LOGOUTANGULAR))
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .exceptionHandling(exc -> exc.accessDeniedHandler(accessDeniedHandler()))
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
  
  @Bean
  @Primary
  RegisteredClientRepository registeredClientRepository(IClientesOauthService clientOauthService) {
    List<ClientesOauth> clientRegis = clientOauthService.findAll();
    
    List<RegisteredClient> registeredClients = clientRegis.stream()
    .map(cli -> RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId(cli.getClientId())
        .clientSecret(cli.getClientSecret())
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        // Forma de autorización tipicas utilizado el estandar OUATH2
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .redirectUri(Arrays.stream(cli.getRedirectUris().split(","))
            .filter(uri -> uri.contains("authorized") || uri.contains("authorize"))
            .findFirst()
            .orElse("http://default-redirect-uri.com"))
        .postLogoutRedirectUri(cli.getPostLogoutRedirectUris())
        .scope(OidcScopes.OPENID)
        .scope(OidcScopes.PROFILE)
        .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(cli.getTimeAccesToken()))
            .refreshTokenTimeToLive(Duration.ofDays(cli.getTimeRefrehsToken())).build())
        // requireAuthorizationConsent(false) se indica a false ya que por defecto los roles son OPENID y PROFILE
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
        .build()).toList();
    return new InMemoryRegisteredClientRepository(registeredClients);
  }
  
  @Bean
  JWKSource<SecurityContext> jwkSource() {
    KeyPair keyPair = generateRsaKey();
    // Necesitamos obtener la llave para poder generar el token de forma local cuando se trabaja con passkeys
    ConstantesApp.keyOauth = keyPair;
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    //exportPublicKey(publicKey);
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    // Necesitamos obtener el mismo UUID para poder generar el token de forma local cuando se trabaja con passkeys
    ConstantesApp.uuidOauth = UUID.randomUUID().toString();
    // Está llave va a quedar registrada en el BE
    RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey)
        .keyID(ConstantesApp.uuidOauth ).build();
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
   
  private static void exportPublicKey(RSAPublicKey publicKey) {
    try (FileOutputStream fos = new FileOutputStream(ConstantesApp.FICHERPEMPLUBLIKEY)) {
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey.getEncoded());
      KeyFactory keyFactory = KeyFactory.getInstance(ConstantesApp.RSA);
      PublicKey pubKey = keyFactory.generatePublic(keySpec);
      fos.write(pubKey.getEncoded());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }*/
	
  @Bean
  JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
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
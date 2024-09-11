package es.sport.buddies.oauth.app.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  /*
  @Bean
  static BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
  */

  @Bean
  @Order(1)
  SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults()); // Enable OpenID                                                                                            // Connect 1.0
    //  Redirigir a la página de inicio de sesión cuando no está autenticado desde el punto final de autorización
    http.exceptionHandling(exceptions -> exceptions.defaultAuthenticationEntryPointFor(
            new LoginUrlAuthenticationEntryPoint("/login"), new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
        // Aceptar tokens de acceso para información de usuario y/o registro de cliente
        .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));
    return http.build();
  }

  @Bean
  @Order(2)
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize
        .anyRequest().authenticated())
        .formLogin(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable());
    return http.build();
  }
  
  @Bean
  UserDetailsService userDetailsService() {
    UserDetails userDetails = User.builder()
        .username("jose")
        .password("{noop}12345")
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(userDetails);
  }

  // Configuración de nuestro cliente FRONT-END
  @Bean
  RegisteredClientRepository registeredClientRepository() {
    RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId("gateway-app")
        .clientSecret("{noop}12345")
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        // Forma de autorización tipicas utilizado el estandar OUATH2
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        // Protegemos el proyecto principal (SPRING CLOUD GATEWAY) - Por defecto, esto no hace falta implementarlo ya que viene
        .redirectUri("http://127.0.0.1:8090/login/oauth2/code/cliente-app")
        // Deberemos de crear un endpoint dentro del cliente (SPRING CLOUD GATEWAY) para poder obtener el código de AUTHORIZATION_CODE para generar el token
        .redirectUri("http://127.0.0.1:8090/authorized")
        // Ruta por defecto cuando se haga logout dentro de la web ( Es opcional cuando trabajamos con servicios REST)
        .postLogoutRedirectUri("http://127.0.0.1:8090/logout")
        // Roles para la aplicación por defecto
        .scope(OidcScopes.OPENID)
        .scope(OidcScopes.PROFILE)
        
        //.scope("read")
        //.scope("write")
        // requireAuthorizationConsent(false) se indica a false ya que por defecto los roles son OPENID y PROFILE
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build()).build();
    return new InMemoryRegisteredClientRepository(oidcClient);
  }

  @Bean
  JWKSource<SecurityContext> jwkSource() {
    KeyPair keyPair = generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    // Está llave va a quedar registrada en el BE
    RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  private static KeyPair generateRsaKey() {
    KeyPair keyPair;
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      keyPair = keyPairGenerator.generateKeyPair();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
    return keyPair;
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
   * Función encargada de agregar información al token que por defecto no se agrega
   * @return
   */
  
  @Bean
  OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
	  return context -> {
		if(context.getTokenType().getValue().equalsIgnoreCase(OAuth2TokenType.ACCESS_TOKEN.getValue())) {
			Authentication principal = context.getPrincipal();
			// Agregamos claims adicionales al token, como roles, direccion, nombres, etc...
			context.getClaims()
			.claim("Datos Adicionales", "Aquí se agrega los claims adicionales que por defecto se agregan")
			.claim("roles", principal.getAuthorities()
					.stream()
					.map(GrantedAuthority:: getAuthority)
					.toList());
		}
	  };
  }
  
  
  
}
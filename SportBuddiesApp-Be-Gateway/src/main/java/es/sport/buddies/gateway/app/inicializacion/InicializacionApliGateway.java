package es.sport.buddies.gateway.app.inicializacion;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

import es.sport.buddies.entity.app.models.entity.ClientesOauth;
import es.sport.buddies.entity.app.models.service.IClientesOauthService;
import es.sport.buddies.gateway.app.constantes.ConstantesGateway;
import jakarta.annotation.PostConstruct;

@Component
public class InicializacionApliGateway {

	@Value("${pass.public.key}")
	private String publicKey;

  @Autowired
  private IClientesOauthService clientOauthService;
	
	@PostConstruct
	public void init() {
		
		ConstantesGateway.FICHERPEMPLUBLIKEY = publicKey;
				
    clientOauthService.findAll().forEach(cliente -> {
      ClientRegistration clientRegistration = mapToClientRegistration(cliente);
      ConstantesGateway.CLIENTREGISTRATIONS.put(clientRegistration.getRegistrationId(), clientRegistration);
    });
		
	}
	
	
	private ClientRegistration mapToClientRegistration(ClientesOauth cli) {
	  return ClientRegistration.withRegistrationId(cli.getClientName())
        .clientId(cli.getClientId())
        .clientSecret(cli.getClientSecret())
        .clientName(cli.getClientName())
        .scope(Arrays.asList(cli.getScopes().split(",")))
        .clientAuthenticationMethod(new ClientAuthenticationMethod(cli.getAuthenticationMethods()))
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUri("http://127.0.0.1:8090/authorized")
        .authorizationUri("http://127.0.0.1:9000/oauth2/authorize")
        .tokenUri("http://127.0.0.1:9000/oauth2/token")
        .jwkSetUri("http://127.0.0.1:9000/.well-known/jwks.json").build();
	}
	
}

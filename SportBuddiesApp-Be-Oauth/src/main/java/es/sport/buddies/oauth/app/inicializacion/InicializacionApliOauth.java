package es.sport.buddies.oauth.app.inicializacion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import es.sport.buddies.oauth.app.constantes.ConstantesApp;
import jakarta.annotation.PostConstruct;

@Component
public class InicializacionApliOauth {

	//@Value("${pass.public.key}")
	//private String publicKey;
	
	@Value("${seguridad.encriptacion}")
	private String encriptacion;
	
	@Value("${redirect.ouaht}")
	private String redirect;
	
	@Value("${endpoint.authorization.oauth}")
	private String authorization;
	
	@Value("${endpoint.logout.oauth}")
	private String logout;
	
	@Value("${client.id.oauth}")
	private String clientId;
	
	@Value("${client.secret.oauth}")
	private String clientSecret;
	
	@Value("${redirect.ouaht.angular}")
	private String redirectAngular;
	
	@Value("${client.id.angular}")
	private String clientIdAngular;
	
	@Value("${client.secret.angular}")
	private String clientSecretAngular;
	
	@Value("${sportbuddies.fe}")
	private String aplicacionFront;
	
	@Value("${issuer.uri.validate.token}")
	private String issuerUriToken;
	
	@PostConstruct
	public void init() {
		
		//ConstantesApp.FICHERPEMPLUBLIKEY = publicKey;
		
		ConstantesApp.RSA = encriptacion;
		
		ConstantesApp.REDIRECTURIOAUTH2 = redirect;
		
		ConstantesApp.ENDPOINTAUTHORIZATION  = authorization;
		
		ConstantesApp.ENDPOINTLOGOUT = logout;
		
		ConstantesApp.CLIENTID = clientId;
		
		ConstantesApp.CLIENTSECRET = clientSecret;
		
	  ConstantesApp.REDIRECTANGULAR = redirectAngular;
	  
	  ConstantesApp.CLIENTIDANGULAR = clientIdAngular;
	  
	  ConstantesApp.CLIENTSECRETANGULAR = clientSecretAngular;
		
	  ConstantesApp.APLICACIONANGULAR = aplicacionFront;
	  
	  ConstantesApp.ISSUERVALIDATETOKEN = issuerUriToken;
	  
	}
	
}
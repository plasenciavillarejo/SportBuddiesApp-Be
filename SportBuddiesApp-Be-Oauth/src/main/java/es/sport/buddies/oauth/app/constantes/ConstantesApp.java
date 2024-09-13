package es.sport.buddies.oauth.app.constantes;

import org.springframework.stereotype.Component;

@Component
public class ConstantesApp {

	private ConstantesApp() {
		// Constructor Vacío
	}
	
	public static final String FICHERPEMPLUBLIKEY = "C:\\SportBuddies_repository\\llave.pem";

	public static final String RSA = "RSA";
	
	public static final String REDIRECTURIOAUTH2 = "http://127.0.0.1:8090/login/oauth2/code/cliente-app";
	
	public static final String ENDPOINTAUTHORIZATION = "http://127.0.0.1:8090/authorized";
	
	public static final String ENDPOINTLOGOUT = "http://127.0.0.1:8090/logout";
	
	public static final String CLIENTID = "gateway-app";
	
	public static final String CLIENTSECRET = "12345";
	
	public static final String ROLE = "ROLE_";
	
}

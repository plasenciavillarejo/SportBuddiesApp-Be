package es.sport.buddies.oauth.app.constantes;

import org.springframework.stereotype.Component;

@Component
public class ConstantesApp {

	private ConstantesApp() {
		// Constructor Vacío
	}
	
	public static String FICHERPEMPLUBLIKEY = "KEY";

	public static String RSA = "SEGURIDAD";
	
	public static String REDIRECTURIOAUTH2 = "REDIRECT";
	
	public static String ENDPOINTAUTHORIZATION = "AUTHORIZATION";
	
	public static String ENDPOINTLOGOUT = "LOGOUT";
	
	public static String CLIENTID = "ID";
	
	public static String CLIENTSECRET = "SECRET";
	
	public static final String ROLE = "ROLE_";
	
}

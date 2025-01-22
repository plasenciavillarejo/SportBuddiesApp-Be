package es.sport.buddies.gateway.app.constantes;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ConstantesGateway {

	private ConstantesGateway() {
		// Constructor Vació
	}
	
	public static String FICHERPEMPLUBLIKEY = "KEY";
	
	public static String APPSPORTBUDDIOAUTH= "OAUTH";
	
	public static final String DOMINIOLOCALHOST = "http://localhost:4200";
	
	public static final String DOMINIOHTTPS = "https://www.sportbuddies.es";
		
}

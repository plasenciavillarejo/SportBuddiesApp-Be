package es.sport.buddies.gateway.app.constantes;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;

@Component
public class ConstantesGateway {

	public ConstantesGateway() {
		// Constructor Vació
	}
	
	public static String FICHERPEMPLUBLIKEY = "KEY";
	
	public static final Map<String, ClientRegistration> CLIENTREGISTRATIONS = new ConcurrentHashMap<>();
	
	public List<ClientRegistration> CLIENTREGISTRATION = null;
	
}

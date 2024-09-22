package es.sport.buddies.gateway.app.inicializacion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import es.sport.buddies.gateway.app.constantes.ConstantesGateway;
import jakarta.annotation.PostConstruct;

@Component
public class InicializacionApliGateway {

	@Value("${pass.public.key}")
	private String publicKey;

	@PostConstruct
	public void init() {
		
		ConstantesGateway.FICHERPEMPLUBLIKEY = publicKey;
		
		
	}
	
}

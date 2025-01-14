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
	
	@PostConstruct
	public void init() {
		
		//ConstantesApp.FICHERPEMPLUBLIKEY = publicKey;
		
		ConstantesApp.RSA = encriptacion;
	  
	}
	
}
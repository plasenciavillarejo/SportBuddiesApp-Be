package es.sport.buddies.main.app.inicializacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import es.sport.buddies.main.app.constantes.ConstantesMain;
import jakarta.annotation.PostConstruct;

@Component
public class Inicializacion {

	private static final Logger LOGGER = LoggerFactory.getLogger(Inicializacion.class);
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Value("${pass.public.key}")
  private String publicKey;

  @Value("${paypal.client.id}")
  private String clientId;
  
  @Value("${paypal.client.secret}")
  private String clientSecret;
  
  @Value("${paypal.mode}")
  private String mode;
	
  @Value("${sportbuddies.gtw}")
  private String appSportBuddiesGtw;
  
  @Value("${sportbuddies.fe}")
  private String appSportBuddiesFe;
  
	@PostConstruct
	public void inicializacion() {
	  
	  ConstantesMain.FICHERPEMPLUBLIKEY = publicKey;
	  
	  ConstantesMain.CLIENTIDPAYPAL = clientId;
	  
	  ConstantesMain.CLIENTSERCRETPAYPAL = clientSecret;
	  
	  ConstantesMain.MODEPAYPAL = mode;
	  
	  ConstantesMain.SPORTBUDDIESGTW = appSportBuddiesGtw;
	  
	  ConstantesMain.SPORTBUDDIESFE = appSportBuddiesFe;
	  
		String password = "12345";
		
		for(int i=0; i<4; i++ ) {
			String passwordEncriptado = passwordEncoder.encode(password);
			LOGGER.info("Imprimiendo contraseñas: {}", passwordEncriptado);
		}
	}
	
}

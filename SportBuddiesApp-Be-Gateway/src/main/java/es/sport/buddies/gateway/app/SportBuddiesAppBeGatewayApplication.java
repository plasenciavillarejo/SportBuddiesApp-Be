package es.sport.buddies.gateway.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//Encargado de leer la entidades ubicado en el proyecto Entity
@EntityScan({"es.sport.buddies.entity.app.models.entity"})
//Encargado de escanear todos los paquetes necesarios para la inyección de las dependencias del proyecto Entity
@SpringBootApplication(scanBasePackages = {"es.sport.buddies.*"})
public class SportBuddiesAppBeGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportBuddiesAppBeGatewayApplication.class, args);
	}

}

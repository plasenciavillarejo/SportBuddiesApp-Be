package es.sport.buddies.entity.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

// Esto es necesario cuando se pretende arrancar el proceso fuera de eclipse, de lo contrario indicara que no encuenta la/s @Entity
@EntityScan({"es.sport.buddies.entity.app.models.entity"})
@SpringBootApplication()
public class SportBuddiesAppBeEntityApplication {

}

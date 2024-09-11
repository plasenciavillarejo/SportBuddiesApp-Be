package es.sport.buddies.oauth.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"es.sport.buddies.entity.app.models.entity"})
public class SportBuddiesAppBeOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportBuddiesAppBeOauthApplication.class, args);
	}

}

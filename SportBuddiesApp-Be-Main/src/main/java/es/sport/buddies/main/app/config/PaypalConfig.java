package es.sport.buddies.main.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.paypal.base.rest.APIContext;

import es.sport.buddies.main.app.constantes.ConstantesMain;

@Configuration
@DependsOn("inicializacion")
public class PaypalConfig {

  @Bean
  APIContext apiContext() {
    return new APIContext(ConstantesMain.CLIENTIDPAYPAL, ConstantesMain.CLIENTSERCRETPAYPAL, ConstantesMain.MODEPAYPAL);
  }

}

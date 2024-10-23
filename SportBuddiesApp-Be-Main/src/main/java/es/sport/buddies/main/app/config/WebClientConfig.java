package es.sport.buddies.main.app.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean(name = "internalWebClient")
	@LoadBalanced
	WebClient.Builder webClient() {
		return WebClient.builder();
	}
	
	 @Bean(name = "externalWebClient")
   WebClient.Builder externalWebClient() {
       return WebClient.builder();
   }
	
}

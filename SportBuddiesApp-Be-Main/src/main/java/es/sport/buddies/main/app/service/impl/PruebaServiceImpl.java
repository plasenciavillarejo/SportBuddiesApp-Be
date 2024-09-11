package es.sport.buddies.main.app.service.impl;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import es.sport.buddies.main.app.service.IPruebaService;

@Service
public class PruebaServiceImpl implements IPruebaService {

	private final WebClient.Builder webClient;
	
	public PruebaServiceImpl(Builder builder) {
		this.webClient = builder;
	}
	
	@Override
	public List<String> resultadoPruebaWebClient() {
		return this.webClient.build()
				.get()
				.uri("http://SportBuddiesApp-Be-Prueba")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				// Convertirmos una colección a una lista de String
				.bodyToFlux(String.class)
				.collectList()
				// Obtenemos la lista de los String, la recibimos
				.block();
	}

}

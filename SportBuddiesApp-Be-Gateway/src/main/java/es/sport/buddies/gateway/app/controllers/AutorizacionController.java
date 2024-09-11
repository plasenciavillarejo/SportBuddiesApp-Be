package es.sport.buddies.gateway.app.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutorizacionController {

	/**
	   * Función encargada de obtener el código de autorizacion, recibe un code que es como se llama en el servidor de autorizacion.
	   * Nos permite intercambiar el código de autorizacion por un token para que podamos acceder a las rutas protegidas.
	   * @param code
	   * @return CODE
	   */
	@GetMapping(value ="/authorized")
	public ResponseEntity<Map<String, String>> autorizacionOauth(@RequestParam String code) {
		return new ResponseEntity<>(Collections.singletonMap("code", code),HttpStatus.OK);
	}

	@GetMapping(value ="/logout")
	public ResponseEntity<Map<String, String>> logout() {
		return new ResponseEntity<>(Collections.singletonMap("Salida de la aplicación", "Correctamente"),HttpStatus.OK);
	}
	
}

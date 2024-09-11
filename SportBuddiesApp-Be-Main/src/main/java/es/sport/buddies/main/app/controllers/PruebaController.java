package es.sport.buddies.main.app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IUsuarioService;

@RestController
public class PruebaController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping(value = "/listar")
	public ResponseEntity<Usuario> pruebaController() {
		Optional<Usuario> usu = usuarioService.findById(1L);
		return new ResponseEntity<>(!usu.isEmpty() ? usu.get() : null ,HttpStatus.OK);
	}
	
}

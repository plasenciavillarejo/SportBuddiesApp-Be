package es.sport.buddies.main.app.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.entity.app.models.entity.Reserva;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IReservaService;
import es.sport.buddies.entity.app.models.service.IUsuarioService;

@RestController
public class PruebaController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IReservaService reservaService;
	
	@GetMapping(value = "/listar")
	public ResponseEntity<Usuario> pruebaController() {
	  UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication(); 
		Optional<Usuario> usu = usuarioService.findById(1L);
		return new ResponseEntity<>(!usu.isEmpty() ? usu.get() : null ,HttpStatus.OK);
	}
	
	@GetMapping(value = "/listarReserva")
	public ResponseEntity<List<Reserva>> listadoReservasUsuario(@RequestParam(name="fechaReserva") @DateTimeFormat(pattern = "yyyy-MM-dd") 
	LocalDate fechaReserva, @RequestParam(name ="idUsuReserva") long idUsuReserva) {
	  List<Reserva> res = null;
	  try {
	    res = reservaService.buscarReservaPorFechaAndIdUsuario(fechaReserva, idUsuReserva);
	  } catch (Exception e) {
      throw e;
    }
	  return new ResponseEntity<>(res,HttpStatus.OK);
	}
	  
	
}

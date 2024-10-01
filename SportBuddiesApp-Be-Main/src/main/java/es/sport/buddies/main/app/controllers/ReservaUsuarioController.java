package es.sport.buddies.main.app.controllers;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.entity.app.dto.ReservaUsuarioDto;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaUsuarioMainService;

@RestController
@RequestMapping(value = "/reservaUsuario")
public class ReservaUsuarioController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaUsuarioMainService reservaUsuarioService;
  
  @GetMapping(value = "/misReservas")
  public ResponseEntity<List<ReservaUsuarioDto>> listadoReservasUsuario(@RequestParam(name="fechaReserva", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") 
  LocalDate fechaReserva, @RequestParam(name = "idReserva") long idReserva) throws ReservaException {
    List<ReservaUsuarioDto> res = null;
    try {
      LOGGER.info("Se recibe fechaReserva: '{}' e IdReserva: '{}' para listar las reservas que contiene el usuario", fechaReserva, idReserva);
      res = reservaUsuarioService.listarReservas(fechaReserva, idReserva);
    } catch (Exception e) {
      throw new ReservaException(e);
    }
    return new ResponseEntity<>(res,HttpStatus.OK);
  }
  
}

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

import es.sport.buddies.entity.app.models.entity.Reserva;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaMainService;

@RestController
@RequestMapping(value = "/reserva")
public class ReservaController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReservaController.class);
  
  @Autowired
  private IReservaMainService reservaMainService;
  
  @GetMapping(value = "/listarReserva")
  public ResponseEntity<List<Reserva>> listadoReservasUsuario(@RequestParam(name="fechaReserva") @DateTimeFormat(pattern = "yyyy-MM-dd") 
  LocalDate fechaReserva) throws ReservaException {
    List<Reserva> res = null;
    try {
      res = reservaMainService.listarReservas(fechaReserva);
    } catch (Exception e) {
      LOGGER.error("Error al buscar la reserva para la fecha {}", fechaReserva);
      throw new ReservaException(e.getMessage(), e.getCause());
    }
    return new ResponseEntity<>(res,HttpStatus.OK);
  }
  
}

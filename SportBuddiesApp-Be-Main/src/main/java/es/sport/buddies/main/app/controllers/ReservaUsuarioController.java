package es.sport.buddies.main.app.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.entity.app.dto.ReservaUsuarioDto;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.CancelarReservaException;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.exceptions.UsuarioException;
import es.sport.buddies.main.app.service.IReservaUsuarioMainService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/reservaUsuario")
public class ReservaUsuarioController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaUsuarioMainService reservaUsuarioService;
  
  @Autowired
  private HttpServletRequest httpRequest;
  
  @GetMapping(value = {"/misReservas", "/historialReservas"})
  public ResponseEntity<List<ReservaUsuarioDto>> listadoReservasUsuario(@RequestParam(name="fechaReserva", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") 
  LocalDate fechaReserva, @RequestParam(name = "idUsuario") long idUsuario) throws ReservaException {
    List<ReservaUsuarioDto> res = null;
    try {
      LOGGER.info("Se recibe fechaReserva: '{}' e IdUsuario: '{}' para listar las reservas que contiene el usuario", fechaReserva, idUsuario);
      res = reservaUsuarioService.listarReservas(fechaReserva, idUsuario, httpRequest.getRequestURI().contains("historialReservas") ? true : false);
    } catch (Exception e) {
      throw new ReservaException(e);
    }
    return new ResponseEntity<>(res,HttpStatus.OK);
  }
 
  @DeleteMapping(value = "/eliminar/{idReservaUsuario}/{idUsuario}")
  public ResponseEntity<Void> cancelarReserva(@PathVariable("idReservaUsuario") long idReservaUsuario, @PathVariable("idUsuario") long idUsuario) throws CancelarReservaException {
    try {
      reservaUsuarioService.eliminarActividad(idReservaUsuario,idUsuario);
    } catch (Exception e) {
      throw new CancelarReservaException(e);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @GetMapping(value = "/obtenerPrecio/{idReservaUsuario}")
  public ResponseEntity<Double> obtenerPrecioActividad(@PathVariable("idReservaUsuario") long idReservaUsuario) throws CancelarReservaException {
    try {
      return new ResponseEntity<>(reservaUsuarioService.obtenerPrecioActividad(idReservaUsuario), HttpStatus.OK);
    } catch (Exception e) {
      throw new CancelarReservaException(e);
    }
  }
  
  @PostMapping(value = "/confirmacion/pago/{idReservaUsuario}")
  public ResponseEntity<Map<String, String>> confirmacionPago(@PathVariable("idReservaUsuario") long idReservaUsuario) throws UsuarioException {
    Map<String, String> mapResponse = new HashMap<>();
    try {
      LOGGER.info("Recibiendo el IdReservaUsuario: {}", idReservaUsuario);
      reservaUsuarioService.confirmacionPago(idReservaUsuario,mapResponse);
    } catch (Exception e) {
      throw new UsuarioException(e);
    }
    return new ResponseEntity<>(mapResponse,HttpStatus.OK);
  }
  
}

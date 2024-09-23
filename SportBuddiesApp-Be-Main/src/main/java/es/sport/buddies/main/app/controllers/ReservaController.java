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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.entity.app.models.entity.Municipio;
import es.sport.buddies.entity.app.models.entity.Reserva;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaMainService;

@RestController
@RequestMapping(value = "/reserva")
public class ReservaController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaMainService reservaMainService;
  
  @GetMapping(value = "/comboInicio")
  public ResponseEntity<Map<String, Object>> comboListadoInicial() throws ReservaException {
    Map<String, Object> mapResult = null;
    try {
      mapResult = reservaMainService.listarCombosPaginaInicial();
    } catch (Exception e) {
      LOGGER.error("Ha sucedido un error ");
      throw new ReservaException(e.getMessage(), e.getCause());
    }
    // Tengo que devolver la tabla deportes, provincias, municipios
    return new ResponseEntity<>(mapResult,HttpStatus.OK);
  }
  
  @GetMapping(value ="/listadoMunicipios")
  public ResponseEntity<Object> listadoMunicipio(@RequestParam(value = "municipio", required = false)
  String provincia) {
    List<String> listMunicipio = null;
    try {
      listMunicipio =  reservaMainService.listaMunicipiosProProvinca(provincia);
    } catch (Exception e) {
      LOGGER.error("Ha sucedido un error ");
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(listMunicipio,HttpStatus.OK);
  }
  
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

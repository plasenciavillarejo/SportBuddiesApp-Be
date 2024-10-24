package es.sport.buddies.main.app.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.entity.app.dto.CrearReservaActividadDto;
import es.sport.buddies.entity.app.dto.InscripcionReservaActividadDto;
import es.sport.buddies.entity.app.dto.ListadoReservaActividadDto;
import es.sport.buddies.entity.app.dto.ReservaActividadDto;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.CrearReservaException;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaActividadMainService;

@RestController
@RequestMapping(value = "/reservaActividad")
public class ReservaActividadController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
    
  @Autowired
  private IReservaActividadMainService reservaActividadMainService;
  
  @GetMapping(value = "/comboInicio")
  public ResponseEntity<Map<String, Object>> comboListadoInicial() throws ReservaException {
    Map<String, Object> mapResult = null;
    try {
      LOGGER.info("Se procede a listar el combon de la página inicial");
      mapResult = reservaActividadMainService.listarCombosPaginaInicial();
      LOGGER.info("Combo cargado correcamente");
    } catch (Exception e) {
      throw new ReservaException(e);
    }
    return new ResponseEntity<>(mapResult,HttpStatus.OK);
  }
  
  @GetMapping(value ="/listadoMunicipios")
  public ResponseEntity<Object> listadoMunicipio(@RequestParam(value = "municipio", required = false)
  String provincia) throws ReservaException {
    List<String> listMunicipio = null;
    try {
      LOGGER.info("Se obtiene el municipio: {}", provincia);
      listMunicipio =  reservaActividadMainService.listaMunicipiosPorProvinca(provincia);
    } catch (Exception e) {
      throw new ReservaException(e);
    }
    return new ResponseEntity<>(listMunicipio,HttpStatus.OK);
  }
    
  @PostMapping(value = "/listadoReserva")
  public ResponseEntity<List<ReservaActividadDto>> listarReservaActividad (@RequestBody ListadoReservaActividadDto listadoDto) throws ReservaException {
    List<ReservaActividadDto> listReservaDto = null;
    try {
      listReservaDto = reservaActividadMainService.listadoReservaActividad(listadoDto);
    } catch (Exception e) {
      throw new ReservaException(e);
    }
    return new ResponseEntity<>(listReservaDto,HttpStatus.OK);
  }
  
  @PostMapping(value = "/crear")
  public ResponseEntity<Object> crearReserva(@RequestBody CrearReservaActividadDto reservaActividadDto) throws CrearReservaException {
    try {
      reservaActividadMainService.crearReservaActivdad(reservaActividadDto);
    } catch (Exception e) {
      throw new CrearReservaException(e);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping(value = "/inscripcion")
  public ResponseEntity<Object> inscripcionReserva(@RequestBody InscripcionReservaActividadDto inscripcionActividad) throws ReservaException, CrearReservaException {
    try {
      LOGGER.info("Se procede a realizar una inscripcion sobre la actividad con ID: {}", inscripcionActividad.getIdReservaActividad());
      reservaActividadMainService.inscripcionReservaActividad(inscripcionActividad);
    } catch (CrearReservaException e) {
      throw e;
    }  catch (ReservaException  e2) {
      throw e2;
    }   
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  /**
   * Función encargada de devolver las actividades en las que está inscrita el usuario para poder habiltar o deshabilitar el botón para 
   * su inscripción en ella.
   */
  @GetMapping(value = "/validarActividad/{idUsuario}")
  public ResponseEntity<List<Long>> validarUsuarioAbonadoActividad(@PathVariable("idUsuario") long idUsuario) throws ReservaException {
    List<Long> listLongActividad = null;
    try { 
      LOGGER.info("Validando las actividades inscritas por el usuario");
      listLongActividad = reservaActividadMainService.listarActividadInscritas(idUsuario);
    } catch (Exception e) {
      throw new ReservaException(e);
    }
    return new ResponseEntity<>(listLongActividad != null ? listLongActividad : Collections.emptyList(), HttpStatus.OK);
  }
  
}

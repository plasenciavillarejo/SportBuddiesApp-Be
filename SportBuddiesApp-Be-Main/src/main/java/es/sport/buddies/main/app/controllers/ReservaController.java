package es.sport.buddies.main.app.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.entity.app.dto.ListadoReservaActividadDto;
import es.sport.buddies.entity.app.dto.ReservaActividadDto;
import es.sport.buddies.entity.app.dto.ReservaUsuarioDto;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaActividadMainService;
import es.sport.buddies.main.app.service.IReservaUsuarioMainService;

@RestController
@RequestMapping(value = "/reserva")
public class ReservaController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaUsuarioMainService reservaMainService;
  
  @Autowired
  private IReservaActividadMainService reservaActividadMainService;
  
  @GetMapping(value = "/comboInicio")
  public ResponseEntity<Map<String, Object>> comboListadoInicial() throws ReservaException {
    Map<String, Object> mapResult = null;
    try {
      LOGGER.info("Se procede a listar el combon de la página inicial");
      mapResult = reservaMainService.listarCombosPaginaInicial();
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
      listMunicipio =  reservaMainService.listaMunicipiosPorProvinca(provincia);
    } catch (Exception e) {
      throw new ReservaException(e);
    }
    return new ResponseEntity<>(listMunicipio,HttpStatus.OK);
  }
  
  @GetMapping(value = "/listarReserva")
  public ResponseEntity<List<ReservaUsuarioDto>> listadoReservasUsuario(@RequestParam(name="fechaReserva") @DateTimeFormat(pattern = "yyyy-MM-dd") 
  LocalDate fechaReserva) throws ReservaException {
    List<ReservaUsuarioDto> res = null;
    try {
      res = reservaMainService.listarReservas(fechaReserva);
    } catch (Exception e) {
      throw new ReservaException(e);
    }
    return new ResponseEntity<>(res,HttpStatus.OK);
  }
  
  @GetMapping(value = "/listadoReservaActividad")
  public List<ReservaActividadDto> listarR (@RequestBody ListadoReservaActividadDto listadoDto) throws ReservaException {
    List<ReservaActividadDto> listReservaDto = null;
    try {
      listReservaDto = reservaActividadMainService.listadoReservaActividad(listadoDto);
    } catch (Exception e) {
      throw new ReservaException(e);
    }
    return listReservaDto;
  }
  
  
}

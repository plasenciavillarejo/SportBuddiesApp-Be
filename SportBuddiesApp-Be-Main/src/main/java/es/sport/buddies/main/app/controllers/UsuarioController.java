package es.sport.buddies.main.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.entity.app.dto.UsuarioDto;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.UsuarioException;
import es.sport.buddies.main.app.service.IUsuarioMainService;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IUsuarioMainService usuarioMainService;
  
  @PostMapping(value = "/crear")
  public ResponseEntity<Void> crearNuevoUsuario(@RequestBody UsuarioDto usuarioDto) throws UsuarioException {
    try {
      LOGGER.info("Se procede a crear al usuario con el nombre: {} ", usuarioDto.getNombreUsuario());
      usuarioMainService.crearNuevoUsuario(usuarioDto);
      LOGGER.info("Usuario creado exitosamente");
    } catch (Exception e) {
      throw new UsuarioException(e);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
  
  @PostMapping(value = "/confirmacion/pago/{idReservaUsuario}")
  public ResponseEntity<Map<String, String>> confirmacionPago(@PathVariable("idReservaUsuario") long idReservaUsuario) throws UsuarioException {
    Map<String, String> mapResponse = new HashMap<>();
    try {
      LOGGER.info("Recibiendo el IdReservaUsuario: {}", idReservaUsuario);
      usuarioMainService.confirmacionPago(idReservaUsuario,mapResponse);
    } catch (Exception e) {
      throw new UsuarioException(e);
    }
    return new ResponseEntity<>(mapResponse,HttpStatus.OK);
    
    
  }
}

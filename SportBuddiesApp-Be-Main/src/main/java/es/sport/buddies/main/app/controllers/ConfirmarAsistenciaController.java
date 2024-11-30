package es.sport.buddies.main.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.entity.app.dto.ConfirmarAsistenciaDto;
import es.sport.buddies.main.app.exceptions.ConfirmarAsistenciaException;
import es.sport.buddies.main.app.service.IConfirmarAsistenciaMainService;

@RestController
@RequestMapping(value = "/confirmar-asistencia")
public class ConfirmarAsistenciaController {
  
  @Autowired
  private IConfirmarAsistenciaMainService confirmarAsistenciaService;
  
  @GetMapping(value = "/usuarios")
  public ResponseEntity<Object> listarAsistentesActividad(ConfirmarAsistenciaDto confirmarAsistenciaDto) throws ConfirmarAsistenciaException {
    Map<String, Object> params = new HashMap<>();
    try {
      params = confirmarAsistenciaService.listarUsuariosConfirmados(confirmarAsistenciaDto);
    }catch (Exception e) {
      throw new ConfirmarAsistenciaException(e);
    }
    return new ResponseEntity<>(params,HttpStatus.OK);
  }
  
  @PostMapping(value = "/guardar")
  public ResponseEntity<Object> guardarConfirmacionUsuario(@RequestBody ConfirmarAsistenciaDto confirmarAsistenciaDto) throws ConfirmarAsistenciaException {
    try {
      confirmarAsistenciaService.almacenarConfirmacionUsuario(confirmarAsistenciaDto);
    } catch (Exception e) {
      throw new ConfirmarAsistenciaException(e);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
 
  @GetMapping(value = "/listadoIdConfirmados/{idUsuario}")
  public ResponseEntity<List<Long>> listadoIdConfirmados(@PathVariable("idUsuario") long idUsuario) throws ConfirmarAsistenciaException {
    try {
      return new ResponseEntity<>(confirmarAsistenciaService.listIdConfirmados(idUsuario), HttpStatus.OK);
    } catch (Exception e) {
      throw new ConfirmarAsistenciaException(e);
    }
  }
  
}

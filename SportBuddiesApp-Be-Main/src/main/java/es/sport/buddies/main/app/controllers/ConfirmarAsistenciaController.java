package es.sport.buddies.main.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.main.app.service.IConfirmarAsistenciaMainService;

@RestController()
@RequestMapping(value = "/confirmar-asistencia")
public class ConfirmarAsistenciaController {

  @Autowired
  private IConfirmarAsistenciaMainService confirmarAsistenciaService;
  
  // Debe ir ConfirmarAsistenciaDtoi en vez del pathVariable
  @GetMapping(value = "/usuarios/{idUsuario}")
  public ResponseEntity<Object> listarAsistentesActividad(@PathVariable("idUsuario") long idUsuario) {
    Map<String, Object> params = new HashMap<>();
    try {
      params = confirmarAsistenciaService.listarUsuariosConfirmados(idUsuario);
    }catch (Exception e) {
      // TODO: handle exception
    }
    return new ResponseEntity<>(params,HttpStatus.OK);
  }
  
}

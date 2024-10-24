package es.sport.buddies.main.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.entity.app.dto.UsuarioDto;
import es.sport.buddies.main.app.exceptions.UsuarioException;
import es.sport.buddies.main.app.service.IUsuarioMainService;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

  @Autowired
  private IUsuarioMainService usuarioMainService;
  
  @PostMapping(value = "/crear")
  public ResponseEntity<Void> crearNuevoUsuario(@RequestBody UsuarioDto usuarioDto) throws UsuarioException {
    try {
      usuarioMainService.crearNuevoUsuario(usuarioDto);
    } catch (Exception e) {
      throw new UsuarioException(e);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
  
}

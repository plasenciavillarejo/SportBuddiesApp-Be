package es.sport.buddies.oauth.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.sport.buddies.entity.app.dto.ClientesOauthDto;
import es.sport.buddies.oauth.app.services.ClienteOauthServiceImpl;

@RestController
@RequestMapping(value = "/clienteOauth")
public class ClienteOauthController {

  @Autowired
  private ClienteOauthServiceImpl clientOauthServiceImpl;
  
  @Autowired
  private PasswordEncoder passEnconder;
  
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(value = "/crear")
  public ResponseEntity<Map<String, String>> crearClienteOauth(@RequestBody ClientesOauthDto clienDto) {
    Map<String, String> result = new HashMap<>();
    RegisteredClient clienteExistente = clientOauthServiceImpl.findById(clienDto.getClientId());
    if(clienteExistente != null) {
      result.put("Error", "El usuario con ID: '"+ clienDto.getClientId() + "' ya está registrado en la aplicación, por favor, registre un nuevo usuario.");
      return new ResponseEntity<>(result,HttpStatus.CONFLICT);
    }
    clienDto.setClientSecret(passEnconder.encode(clienDto.getClientSecret()));
    clientOauthServiceImpl.guardar(clienDto);
    result.put("Success", "Cliente Oauth2 almacenado exitosamente");
    return new ResponseEntity<>(result,HttpStatus.CREATED);
  }
  
}

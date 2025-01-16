package es.sport.buddies.oauth.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webauthn4j.data.PublicKeyCredentialCreationOptions;

import es.sport.buddies.entity.app.dto.LoginPassKeyNavigationDto;
import es.sport.buddies.entity.app.dto.PasskeyCredentialDto;
import es.sport.buddies.entity.app.dto.PasskeyDto;
import es.sport.buddies.oauth.app.exceptions.PasskeyException;
import es.sport.buddies.oauth.app.service.impl.PassKeyServiceImpl;

@RestController
@RequestMapping(value = "/passkeys2")
public class PasskeyController2 {

  @Autowired
  private PassKeyServiceImpl passkeyServiceImpl;

  @GetMapping("/token")
  public ResponseEntity<Map<String, Object>> token() {
    return new ResponseEntity<>(Map.of("token-generado", passkeyServiceImpl.token()), HttpStatus.OK);
  }
  
}
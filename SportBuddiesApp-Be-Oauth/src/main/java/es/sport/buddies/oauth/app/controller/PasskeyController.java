package es.sport.buddies.oauth.app.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.webauthn4j.data.PublicKeyCredentialCreationOptions;

import es.sport.buddies.entity.app.dto.LoginPassKeyNavigationDto;
import es.sport.buddies.entity.app.dto.PasskeyCredentialDto;
import es.sport.buddies.entity.app.dto.PasskeyDto;
import es.sport.buddies.oauth.app.exceptions.PasskeyException;
import es.sport.buddies.oauth.app.service.impl.PassKeyServiceImpl;

@RestController
@RequestMapping(value = "/passkeys")
public class PasskeyController {

  @Autowired
  private PassKeyServiceImpl passkeyServiceImpl;

  @PostMapping(value = "/register")
  public ResponseEntity<PublicKeyCredentialCreationOptions> startRegistration(@RequestBody PasskeyDto request) throws PasskeyException {
    return new ResponseEntity<>(passkeyServiceImpl.generateRegistrationOptions(request), HttpStatus.OK);
  }

  @PostMapping("/validar-registro")
  public ResponseEntity<Object> register(@RequestBody PasskeyCredentialDto credentialDto) throws StreamReadException, DatabindException, IOException {
    return new ResponseEntity<>(Map.of("response", passkeyServiceImpl.validarRegistro(credentialDto)), HttpStatus.OK);
  }
  
  @GetMapping("/generate-challenge")
  public ResponseEntity<Map<String, Object>> generateLoginChallenge() {
    return new ResponseEntity<>(Map.of("code-challenge", passkeyServiceImpl.generateChallengeLogin()), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<Object> validateLogin(@RequestBody LoginPassKeyNavigationDto loginPassKeyNavigationDto) throws PasskeyException {
    return new ResponseEntity<>(passkeyServiceImpl.validateAssertion(loginPassKeyNavigationDto), HttpStatus.OK);
  }

}
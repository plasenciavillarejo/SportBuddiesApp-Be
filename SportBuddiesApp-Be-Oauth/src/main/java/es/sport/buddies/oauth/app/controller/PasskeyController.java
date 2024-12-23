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
import es.sport.buddies.oauth.app.service.impl.PassKeyServiceImpl;

@RestController
@RequestMapping(value = "/passkeys")
public class PasskeyController {

  @Autowired
  private PassKeyServiceImpl passkeyServiceImpl;

  @PostMapping(value = "/register")
  public ResponseEntity<PublicKeyCredentialCreationOptions> startRegistration(@RequestBody PasskeyDto request) {
    return new ResponseEntity<>(passkeyServiceImpl.generateRegistrationOptions(request), HttpStatus.OK);
  }

  @PostMapping("/validar-registro")
  public ResponseEntity<Object> register(@RequestBody PasskeyCredentialDto credentialDto) {
    return new ResponseEntity<>(Map.of("response", passkeyServiceImpl.validarRegistro(credentialDto)), HttpStatus.OK);
  }
  
  @GetMapping("/generate-challenge")
  public ResponseEntity<Map<String, Object>> generateLoginChallenge() {
    return new ResponseEntity<>(Map.of("code-challenge", passkeyServiceImpl.generateChallengeLogin()), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<Object> validateLogin(@RequestBody LoginPassKeyNavigationDto loginPassKeyNavigationDto) throws Exception {
    return new ResponseEntity<>(passkeyServiceImpl.validateAssertion(loginPassKeyNavigationDto), HttpStatus.OK);
  }
  
  
  /*
   * @PostMapping("/register/finish") public ResponseEntity<?>
   * finishRegistration(@RequestBody PasskeyRegistrationResponse response) { //
   * Valida la respuesta de registro
   * passkeyService.validateRegistrationResponse(response); return
   * ResponseEntity.ok("Passkey registrada con éxito"); }
   */
  
  /*
  
  @PostMapping("/authenticate")
public PublicKeyCredentialRequestOptions startAuthentication(@RequestBody AuthRequest request) {
    // Genera opciones de autenticación
    PublicKeyCredentialRequestOptions options = passkeyService.generateAuthenticationOptions(request);
    return options;
}

@PostMapping("/authenticate/finish")
public ResponseEntity<?> finishAuthentication(@RequestBody AuthResponse response) {
    // Valida la respuesta de autenticación
    passkeyService.validateAuthenticationResponse(response);
    return ResponseEntity.ok("Autenticación exitosa");
}
  
  
  */
}
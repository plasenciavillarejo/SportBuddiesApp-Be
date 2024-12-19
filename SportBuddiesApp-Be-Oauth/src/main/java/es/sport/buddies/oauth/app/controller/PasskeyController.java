package es.sport.buddies.oauth.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webauthn4j.data.PublicKeyCredentialCreationOptions;

import es.sport.buddies.entity.app.dto.PasskeyDto;
import es.sport.buddies.oauth.app.service.impl.PassKeyServiceImpl;

@RestController
@RequestMapping(value = "/passkeys")
public class PasskeyController {

  @Autowired
  private PassKeyServiceImpl passkeyServiceImpl;

  @PostMapping(value = "/register")
  public PublicKeyCredentialCreationOptions startRegistration(@RequestBody PasskeyDto request) {
    return passkeyServiceImpl.generateRegistrationOptions(request);
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
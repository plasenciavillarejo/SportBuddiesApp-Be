package es.sport.buddies.oauth.app.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.webauthn4j.converter.AttestationObjectConverter;
import com.webauthn4j.converter.CollectedClientDataConverter;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.AttestationConveyancePreference;
import com.webauthn4j.data.AuthenticatorSelectionCriteria;
import com.webauthn4j.data.PublicKeyCredentialCreationOptions;
import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.PublicKeyCredentialRpEntity;
import com.webauthn4j.data.PublicKeyCredentialType;
import com.webauthn4j.data.PublicKeyCredentialUserEntity;
import com.webauthn4j.data.ResidentKeyRequirement;
import com.webauthn4j.data.UserVerificationRequirement;
import com.webauthn4j.data.attestation.AttestationObject;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import com.webauthn4j.data.client.CollectedClientData;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.util.Base64UrlUtil;

import es.sport.buddies.entity.app.dto.PasskeyCredentialDto;
import es.sport.buddies.entity.app.dto.PasskeyDto;

@Service
public class PassKeyServiceImpl {

  
  
  // Convertidores de la librería webauthn4j
  private final AttestationObjectConverter attestationObjectConverter;
  private final CollectedClientDataConverter clientDataConverter;
  
  public PassKeyServiceImpl () {
 // Inicializar los convertidores de objetos
    this.attestationObjectConverter = new AttestationObjectConverter(new ObjectConverter());
    this.clientDataConverter = new CollectedClientDataConverter(new ObjectConverter());
  }
  
  private static final Logger LOGGER = LoggerFactory.getLogger(PassKeyServiceImpl.class);
  
  public PublicKeyCredentialCreationOptions generateRegistrationOptions(PasskeyDto request) {
    PublicKeyCredentialCreationOptions a = new PublicKeyCredentialCreationOptions(
        new PublicKeyCredentialRpEntity(request.getRpId(),"SportBuddiesApp"),
        new PublicKeyCredentialUserEntity(UUID.randomUUID().toString()
            .getBytes(StandardCharsets.UTF_8), 
            request.getUsername(), 
            request.getDisplayName()),
        generateChallenge(),
        // Configurar los parámetros de clave pública
        List.of(new PublicKeyCredentialParameters(PublicKeyCredentialType.PUBLIC_KEY,COSEAlgorithmIdentifier.EdDSA),
            new PublicKeyCredentialParameters(PublicKeyCredentialType.PUBLIC_KEY,COSEAlgorithmIdentifier.ES256),
            new PublicKeyCredentialParameters(PublicKeyCredentialType.PUBLIC_KEY,COSEAlgorithmIdentifier.RS256)),
        // Timeout en milisegundos
        300000L,
        // Exclude credentials (opcional)
        new ArrayList<>(),
        // Configurar los criterios de selección del autenticador
        new AuthenticatorSelectionCriteria(
            null,
            // Requerir autenticador residente
            ResidentKeyRequirement.DISCOURAGED,
            UserVerificationRequirement.PREFERRED),
        // Preferencia de attestation
        AttestationConveyancePreference.NONE,
        // Extensiones (opcional)
        null
    );
    LOGGER.info("Datos: {}", a);
    
    return a;
  }

  private Challenge generateChallenge() {
    SecureRandom random = new SecureRandom();
    byte[] challengeBytes = new byte[32];
    // Llena challengeBytes con datos aleatorios.
    random.nextBytes(challengeBytes);
    return new DefaultChallenge(challengeBytes);
  }
  
  
  public void validarRegistro(PasskeyCredentialDto credentialDto) {
 // Decodificar el attestationObject desde Base64
    byte[] attestationObjectBytes = Base64UrlUtil.decode(credentialDto.getResponse().getAttestationObject());
    AttestationObject attestationObject = attestationObjectConverter.convert(attestationObjectBytes);

    // Decodificar el clientDataJSON desde Base64
    byte[] clientDataJSONBytes = Base64UrlUtil.decode(credentialDto.getResponse().getClientDataJSON());
    CollectedClientData clientData = clientDataConverter.convert(clientDataJSONBytes);

    // Validar los datos de clientData
    if (!clientData.getType().getValue().equalsIgnoreCase("webauthn.create")) {
      throw new IllegalArgumentException("El tipo de clientData no es válido.");
  }

    // Procesar el attestationObject (verificación de la clave pública)
    if (attestationObject.getAuthenticatorData().getAttestedCredentialData() != null) {
      LOGGER.info("Clave pública: {}", attestationObject.getAuthenticatorData().getAttestedCredentialData().getCredentialId());
  } else {
      throw new IllegalArgumentException("El attestationObject no contiene datos de clave pública.");
  }
  }
  
}

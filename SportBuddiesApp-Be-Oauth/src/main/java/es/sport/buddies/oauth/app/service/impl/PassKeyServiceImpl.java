package es.sport.buddies.oauth.app.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.webauthn4j.data.AttestationConveyancePreference;
import com.webauthn4j.data.AuthenticatorSelectionCriteria;
import com.webauthn4j.data.PublicKeyCredentialCreationOptions;
import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.PublicKeyCredentialRpEntity;
import com.webauthn4j.data.PublicKeyCredentialType;
import com.webauthn4j.data.PublicKeyCredentialUserEntity;
import com.webauthn4j.data.ResidentKeyRequirement;
import com.webauthn4j.data.UserVerificationRequirement;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.client.challenge.DefaultChallenge;

import es.sport.buddies.entity.app.dto.PasskeyDto;

@Service
public class PassKeyServiceImpl {

  public PublicKeyCredentialCreationOptions generateRegistrationOptions(PasskeyDto request) {
    // Configurar las opciones de creación de credenciales
    return  new PublicKeyCredentialCreationOptions(
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
            ResidentKeyRequirement.REQUIRED,
            UserVerificationRequirement.PREFERRED),
        // Preferencia de attestation
        AttestationConveyancePreference.DIRECT,
        // Extensiones (opcional)
        null
    );
  }

  private Challenge generateChallenge() {
    SecureRandom random = new SecureRandom();
    byte[] challengeBytes = new byte[32];
    // Llena challengeBytes con datos aleatorios.
    random.nextBytes(challengeBytes);
    return new DefaultChallenge(challengeBytes);
  }
  
}

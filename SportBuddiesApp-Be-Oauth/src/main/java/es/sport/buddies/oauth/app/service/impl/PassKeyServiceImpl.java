package es.sport.buddies.oauth.app.service.impl;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import es.sport.buddies.entity.app.dto.LoginPassKeyNavigationDto;
import es.sport.buddies.entity.app.dto.PasskeyCredentialDto;
import es.sport.buddies.entity.app.dto.PasskeyDto;
import es.sport.buddies.entity.app.models.dao.IUsuariosDao;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.entity.UsuarioPassKey;
import es.sport.buddies.entity.app.models.service.IUsuarioPassKeyService;
import es.sport.buddies.oauth.app.constantes.ConstantesApp;

@Service
public class PassKeyServiceImpl {

  @Autowired
  private IUsuarioPassKeyService usuarioPasskeyService;
  
  @Autowired
  private IUsuariosDao usuarioDao;
  
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
    return new PublicKeyCredentialCreationOptions(
        new PublicKeyCredentialRpEntity(request.getRpId(),"SportBuddiesApp"),
        new PublicKeyCredentialUserEntity(UUID.randomUUID().toString()
            .getBytes(StandardCharsets.UTF_8), 
            request.getUsername(), 
            request.getDisplayName()),
        generateChallenge(),
        /** Configurar los parámetros de clave pública:
         * Especificar múltiples algoritmos permite a los dispositivos elegir uno compatible con su configuración.
         * De está forma, cuando el cliente recie la lista de algoritmos, selecciona el que mejor soporta, haciendo que nuestra aplicación
         * sea más flexible y no dependa de un único tipo de dispositivo
         */
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
  }

  
  /**
   * Función encargada de validar el registro una vez que ha sido creado desde el navegador
   * @param credentialDto
   * @return
   */
  public String validarRegistro(PasskeyCredentialDto credentialDto) {
    LOGGER.info("Validando la existencia del usuario: {}", credentialDto.getNombreUsuario());
    
    Usuario usuario = Optional.ofNullable(usuarioDao.findByNombreUsuario(credentialDto.getNombreUsuario()))
        .or(() -> Optional.ofNullable(usuarioDao.findByEmail(credentialDto.getNombreUsuario())))
        .orElseThrow(() -> new IllegalArgumentException("El usuario " + credentialDto.getNombreUsuario() + " no existe dentro de la aplicación"));
    
    LOGGER.info("El usuario existe en la aplicación, se continua con la validación del registro");

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

    if (attestationObject.getAuthenticatorData().getAttestedCredentialData() != null) {
      ECPublicKey publicKey = (ECPublicKey) attestationObject.getAuthenticatorData().getAttestedCredentialData()
          .getCOSEKey().getPublicKey();
      
      LOGGER.info("Llave X: {}", publicKey.getW().getAffineX());
      LOGGER.info("Llave Y: {}", publicKey.getW().getAffineY());
      // curva("P-256")
      UsuarioPassKey usuPassKey = UsuarioPassKey.builder()
          .usuarios(usuario)
          .llavePublica(Base64.getEncoder().encodeToString(publicKey.getEncoded()))
          .credencialId(base64UrlToBase64(Base64.getEncoder()
              .encodeToString(attestationObject.getAuthenticatorData()
                  .getAttestedCredentialData().getCredentialId())).replace("==", ""))
          .algoritmo(attestationObject.getAuthenticatorData().getAttestedCredentialData()
              .getCOSEKey().getAlgorithm().toString())
          .fechaCreacion(LocalDate.now())
          .build();  
      
      LOGGER.info("Almacenando al usuario passkesy: {}", usuPassKey);
      try {
        usuarioPasskeyService.guardarUsuarioPasskeys(usuPassKey);
      } catch (Exception e) {
        throw new Error("No se ha podido almacenar correctamente el passkey");
      }
    } else {
      throw new IllegalArgumentException("El attestationObject no contiene datos de clave pública.");
    }
    return "Credencial procesada correctamente.";
  }

  /**
   * Función encargada de validar con la llave que está entrando el usuario
   * @param credentialId
   * @param challengeFromFrontend
   * @param clientDataJSON
   * @param authenticatorData
   * @param signature
   * @return
   * @throws Exception 
   */
  public boolean validateAssertion(LoginPassKeyNavigationDto loginPassKeyNavigationDto) throws Exception {

    LOGGER.info("Se procede a obtener la credential_id en el caso de que el usuario este registrado");
    Optional<UsuarioPassKey> usuPass = Optional
        .ofNullable(usuarioPasskeyService.findByCredencialId(loginPassKeyNavigationDto.getCredentialId()));

    if (loginPassKeyNavigationDto.getCredentialId() == null) {
      throw new RuntimeException("Credencial no encontrada");
    }
    
    // Validar el challenge recibido, 
    if (!validateChallenge(loginPassKeyNavigationDto.getChallangeGenerateBe(), ConstantesApp.CODECHALLENGEBE)) {
      throw new RuntimeException("El challenge no es válido");
    }

    // Validar la firma utilizando la clave pública almacenada
    PublicKey publicKey = decodificarLlavePublicaBe(usuPass.get().getLlavePublica());

    // Verificar la firma de la autenticación
    boolean signatureValid = verifySignature(publicKey, loginPassKeyNavigationDto.getAuthenticatorData(),
        loginPassKeyNavigationDto.getClientDataJson(), loginPassKeyNavigationDto.getSignature());

    if (!signatureValid) {
      throw new RuntimeException("Firma inválida");
    }

    return true;
  }

  private boolean validateChallenge(String challengeFromFrontend, String challengeBe) {
      return challengeFromFrontend.equalsIgnoreCase(challengeBe);
  }

  
  private boolean verifySignature(PublicKey publicKey, String authenticatorData, String clientDataJSON,
      String signature) {
    try {
      // Convertir la firma de URL-safe Base64 a Base64 estándar
      String base64Signature = signature.replace('-', '+').replace('_', '/');

      // Asegurarse de que la firma tenga la longitud correcta (múltiplo de 4)
      int paddingLength = (4 - base64Signature.length() % 4) % 4;
      base64Signature += "=".repeat(paddingLength);

      // Decodificar la firma con Base64 estándar
      byte[] decodedSignature = Base64.getDecoder().decode(base64Signature);  // Aquí usas la firma ya convertida

      LOGGER.info("Firma decodificada: {}" , Arrays.toString(decodedSignature));

      // Verificar la firma
      Signature sig = Signature.getInstance("SHA256withECDSA");
      sig.initVerify(publicKey);
      
      LOGGER.info("Datos de autenticación: {}" , authenticatorData);
      LOGGER.info("Datos del cliente: {}" , clientDataJSON);

      // Usamos los datos en su formato binario, por eso convertimos a bytes
      sig.update(authenticatorData.getBytes(StandardCharsets.UTF_8)); // En este caso el 'authenticatorData' es un String
      sig.update(clientDataJSON.getBytes(StandardCharsets.UTF_8));    // Lo mismo para 'clientDataJSON'

      boolean isValid = sig.verify(decodedSignature);
      LOGGER.info("Resultado de la verificación: " + isValid);
      return isValid;
    } catch (Exception e) {
      throw new RuntimeException("Error al verificar la firma", e);
    }
  }
  
  
  /**
   * Decodificamos la llave publica 
   * @param base64PublicKey
   * @return
   * @throws Exception
   */
  private PublicKey decodificarLlavePublicaBe(String base64PublicKey) throws Exception {
    /*byte[] decodedKey = Base64.getDecoder().decode(base64PublicKey);
    KeyFactory keyFactory = KeyFactory.getInstance("EC");
    return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));*/
    return reconstruiLlavePublica(new BigInteger("15418210193393438647361277688816180959016573880402153664484426201424376283833"),
        new BigInteger("114369761407809951100245863160254984994447130994177379634978877816733271057570"));
  }
  
  public String generateChallengeLogin() {
    ConstantesApp.CODECHALLENGEBE = Base64.getUrlEncoder().withoutPadding().encodeToString(generateChallengeBytes());
    return ConstantesApp.CODECHALLENGEBE; 
  }
  
  private byte[] generateChallengeBytes() {
    SecureRandom random = new SecureRandom();
    byte[] challengeBytes = new byte[32];
    random.nextBytes(challengeBytes); // Llena los bytes con valores aleatorios
    return challengeBytes;
}
  
  private Challenge generateChallenge() {
    SecureRandom random = new SecureRandom();
    byte[] challengeBytes = new byte[32];
    // Llena challengeBytes con datos aleatorios.
    random.nextBytes(challengeBytes);
    return new DefaultChallenge(challengeBytes);
  }
 
  private String base64UrlToBase64(String base64Url) {
    return base64Url.replace('-', '+').replace('_', '/');
  }

  private PublicKey reconstruiLlavePublica(BigInteger x, BigInteger y) throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeySpecException {
    ECPoint point = new ECPoint(x, y);
    // Obtener el ECParameterSpec para la curva "secp256r1"
    AlgorithmParameters params = AlgorithmParameters.getInstance("EC");
    params.init(new ECGenParameterSpec("secp256r1"));
    ECParameterSpec ecSpec = params.getParameterSpec(ECParameterSpec.class);       
    ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(point, ecSpec);
    KeyFactory keyFactory = KeyFactory.getInstance("EC");
    return keyFactory.generatePublic(publicKeySpec);
  }
  
}

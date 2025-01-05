package es.sport.buddies.oauth.app.service.impl;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.ECPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import es.sport.buddies.entity.app.models.entity.CodeChallange;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.entity.UsuarioPassKey;
import es.sport.buddies.entity.app.models.service.ICodeChallangeService;
import es.sport.buddies.entity.app.models.service.IRoleService;
import es.sport.buddies.entity.app.models.service.IUsuarioPassKeyService;
import es.sport.buddies.entity.app.models.service.IUsuarioService;
import es.sport.buddies.oauth.app.constantes.ConstantesApp;
import es.sport.buddies.oauth.app.exceptions.PasskeyException;

@Service
public class PassKeyServiceImpl {

  @Autowired
  private IUsuarioPassKeyService usuarioPasskeyService;

  @Autowired
  private IUsuariosDao usuarioDao;

  @Autowired
  private UserDetailsService userDetailService;

  @Autowired
  private JwtServiceImpl jwtServieImpl;
  
  @Autowired
  private BCryptPasswordEncoder bCryptPass;
  
  @Autowired
  private IRoleService roleService;
  
  @Autowired
  private IUsuarioService usuariosService;
  
  @Autowired
  private ICodeChallangeService codeChallangeService;
  
  // Convertidores de la librería webauthn4j
  private final AttestationObjectConverter attestationObjectConverter;
  private final CollectedClientDataConverter clientDataConverter;

  public PassKeyServiceImpl() {
    // Inicializar los convertidores de objetos
    this.attestationObjectConverter = new AttestationObjectConverter(new ObjectConverter());
    this.clientDataConverter = new CollectedClientDataConverter(new ObjectConverter());
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(PassKeyServiceImpl.class);

  /**
   * Función encargada de generar un passkeys para el inicio de la aplicación.
   * @param request
   * @return
   * @throws PasskeyException
   */
  public PublicKeyCredentialCreationOptions generateRegistrationOptions(PasskeyDto request) throws PasskeyException {
    
    LOGGER.info("Validando si el usuario tiene una cuenta creada...");

    Usuario usuario = Optional.ofNullable(usuarioDao.findByNombreUsuario(request.getUsername()))
        .or(() -> Optional.ofNullable(usuarioDao.findByEmail(request.getUsername())))
        .orElseGet(() -> {
            LOGGER.info("El usuario no existe, procediendo a crear uno nuevo.");
            return crearUsuario(request.getUsername());
        });

    guardarUsuario(usuario);
    
    return new PublicKeyCredentialCreationOptions(new PublicKeyCredentialRpEntity(request.getRpId(), "SportBuddiesApp"),
        new PublicKeyCredentialUserEntity(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8),
            request.getUsername(), request.getDisplayName()),
        generateChallenge(),
        /**
         * Configurar los parámetros de clave pública: Especificar múltiples algoritmos
         * permite a los dispositivos elegir uno compatible con su configuración. De
         * está forma, cuando el cliente recie la lista de algoritmos, selecciona el que
         * mejor soporta, haciendo que nuestra aplicación sea más flexible y no dependa
         * de un único tipo de dispositivo
         */
        List.of(new PublicKeyCredentialParameters(PublicKeyCredentialType.PUBLIC_KEY, COSEAlgorithmIdentifier.EdDSA),
            new PublicKeyCredentialParameters(PublicKeyCredentialType.PUBLIC_KEY, COSEAlgorithmIdentifier.ES256),
            new PublicKeyCredentialParameters(PublicKeyCredentialType.PUBLIC_KEY, COSEAlgorithmIdentifier.RS256)),
        // Timeout en milisegundos
        300000L,
        // Exclude credentials (opcional)
        new ArrayList<>(),
        // Configurar los criterios de selección del autenticador
        new AuthenticatorSelectionCriteria(null,
            // Requerir autenticador residente
            ResidentKeyRequirement.DISCOURAGED, UserVerificationRequirement.PREFERRED),
        // Preferencia de attestation
        AttestationConveyancePreference.NONE,
        // Extensiones (opcional)
        null);
  }

  /**
   * Función encargada de validar el registro una vez que ha sido creado desde el
   * navegador
   * 
   * @param credentialDto
   * @return
   */
  public String validarRegistro(PasskeyCredentialDto credentialDto) {
    LOGGER.info("Validando la existencia del usuario: {}", credentialDto.getNombreUsuario());

    Usuario usuario = Optional.ofNullable(usuarioDao.findByNombreUsuario(credentialDto.getNombreUsuario()))
        .or(() -> Optional.ofNullable(usuarioDao.findByEmail(credentialDto.getNombreUsuario())))
        .orElseThrow(() -> new IllegalArgumentException(
            "El usuario " + credentialDto.getNombreUsuario() + " no existe dentro de la aplicación"));

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
      UsuarioPassKey usuPassKey = UsuarioPassKey.builder().usuarios(usuario)
          .llavePublica(Base64.getEncoder().encodeToString(publicKey.getEncoded()))
          .credencialId(base64UrlToBase64(Base64.getEncoder()
              .encodeToString(attestationObject.getAuthenticatorData().getAttestedCredentialData().getCredentialId())))
          .algoritmo(attestationObject.getAuthenticatorData().getAttestedCredentialData().getCOSEKey().getAlgorithm()
              .toString())
          .fechaCreacion(LocalDate.now()).build();
      try {
        LOGGER.info("Se procede almacenar al usuario-passkesy: {}", usuPassKey);
        usuarioPasskeyService.guardarUsuarioPasskeys(usuPassKey);
        LOGGER.info("Usuario-Passkey almacenado exitosamente");
      } catch (Exception e) {
        throw new Error("No se ha podido almacenar correctamente al usuario-passkey.");
      }
    } else {
      throw new IllegalArgumentException("El attestationObject no contiene datos de clave pública.");
    }
    return "Credencial procesada correctamente.";
  }

  /**
   * Función encargada de validar con la llave que está entrando el usuario
   * 
   * @param credentialId
   * @param challengeFromFrontend
   * @param clientDataJSON
   * @param authenticatorData
   * @param signature
   * @return
   * @throws PasskeyException 
   * @throws Exception
   */
  public Map<String, String> validateAssertion(LoginPassKeyNavigationDto loginPassKeyNavigationDto)
      throws PasskeyException {
    LOGGER.info("Se procede a obtener la credential_id en el caso de que el usuario este registrado");
    Optional<UsuarioPassKey> usuPass = Optional
        .ofNullable(usuarioPasskeyService.findByCredencialId(loginPassKeyNavigationDto.getCredentialId()));
    boolean signatureValid = false;
    Map<String, String> tokenGenerado = new HashMap<>();
    if (usuPass.isPresent()) {
      if (loginPassKeyNavigationDto.getCredentialId() == null) {
        throw new RuntimeException("Credencial no encontrada");
      }
      CodeChallange codeChallange = codeChallangeService.findByCodeChallange(loginPassKeyNavigationDto.getChallangeGenerateBe());
      
      // Validar el challenge recibido,
      if (!loginPassKeyNavigationDto.getChallangeGenerateBe().equalsIgnoreCase(codeChallange.getCodeChallange())) {
        throw new PasskeyException("El challenge no es válido");
      }
      LOGGER.info("Procediendo a borrar el code-challange validado");
      codeChallangeService.eliminarCodeChallangeValidado(codeChallange.getIdCodeChallange());
      LOGGER.info("Registro borrado exitosamente");
      
      PublicKey publicKey = null;
      try {
        publicKey = decodificarLlavePublicaBe(usuPass.get().getLlavePublica()
            .replace("-", "+").replace("_", "/").replace("=", ""));
      } catch (Exception e) {
        throw new PasskeyException("Error en la decodificacion de la firma");
      }
      // Obtenemos la llave publica almacenada en BBDD

      // Verificar la firma de la autenticación
      signatureValid = verifySignature(publicKey, loginPassKeyNavigationDto.getAuthenticatorData(),
          loginPassKeyNavigationDto.getClientDataJson(), loginPassKeyNavigationDto.getSignature());

      if (!signatureValid) {
        throw new PasskeyException("Firma inválida");
      }
      // Volvemos a validar al usuari para aseguranos de que es correcto.
      UserDetails userDetails = userDetailService.loadUserByUsername(usuPass.get().getUsuarios().getNombreUsuario());

      tokenGenerado = Map.ofEntries(
          Map.entry("access_token",
              jwtServieImpl.generateToken(usuPass.get().getUsuarios().getNombreUsuario(), ConstantesApp.CLIENTIDANGULAR,
                  userDetails.getAuthorities().stream().map(rol -> rol.getAuthority()).toList(),
                  Arrays.asList("openid", "profile"), usuPass.get().getUsuarios().getIdUsuario())),
          Map.entry("refresh_token",
              jwtServieImpl.generateRefreshToken(usuPass.get().getUsuarios().getNombreUsuario(),
                  ConstantesApp.CLIENTIDANGULAR,
                  userDetails.getAuthorities().stream().map(rol -> rol.getAuthority()).toList(),
                  Arrays.asList("openid", "profile"), usuPass.get().getUsuarios().getIdUsuario())));
    } else {
      LOGGER.error("La credencial {}, no se encuentra registrada en la aplicación.",
          loginPassKeyNavigationDto.getCredentialId());
      throw new PasskeyException("Las credenciales proporcionadas no están registradas en nuestro sistema. "
          + "Le invitamos a crear una cuenta para acceder a la aplicación.");
    }
    return tokenGenerado;
  }

  /**
   * Función encargada de validar el clientDataJson y el authenticatodData
   * mediante la clave publica enviada al BE.
   * 
   * @param publicKey
   * @param authenticatorData
   * @param clientDataJson
   * @param signature
   * @return
   */
  private boolean verifySignature(PublicKey publicKey, String authenticatorData, String clientDataJson,
      String signature) {
    try {
      byte[] decodedAuthenticatorData = convertirBase64UrlDecode(authenticatorData);
      byte[] decodedClientDataJson = convertirBase64UrlDecode(clientDataJson);
      byte[] decodedSignature = convertirBase64UrlDecode(signature);

      if (decodedAuthenticatorData.length == 0 || decodedClientDataJson.length == 0 || decodedSignature.length == 0) {
        throw new IllegalArgumentException("No se pueden enviar datos nulos o vacíos.");
      }
      Signature sig = Signature.getInstance("SHA256withECDSA");
      sig.initVerify(publicKey);

      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] clientDataHash = digest.digest(decodedClientDataJson);

      byte[] signedData = ByteBuffer.allocate(decodedAuthenticatorData.length + clientDataHash.length)
          .put(decodedAuthenticatorData).put(clientDataHash).array();
      sig.update(signedData);
      return sig.verify(decodedSignature);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e.getCause());
      return false;
    }
  }

  /**
   * Decodificamos la llave publica
   * 
   * @param base64PublicKey
   * @return
   * @throws Exception
   */
  private PublicKey decodificarLlavePublicaBe(String base64PublicKey) throws Exception {
    byte[] decodedKey = Base64.getDecoder().decode(base64PublicKey);
    KeyFactory keyFactory = KeyFactory.getInstance("EC");
    return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
  }

  /**
   * Función encargada de genrera el code Challange que será enviado al front que posteriormente se validara
   * con el login para validar que es correcto.
   * @return
   */
  public String generateChallengeLogin() {
    CodeChallange codeChallange = CodeChallange.builder().codeChallange(
        Base64.getUrlEncoder().withoutPadding()
        .encodeToString(generateChallengeBytes()))
        .build();
    codeChallangeService.guardarCodeChallange(codeChallange);
    return codeChallange.getCodeChallange();
  }

  private Challenge generateChallenge() {
    return new DefaultChallenge(generateChallengeBytes());
  }
  
  private byte[] generateChallengeBytes() {
    SecureRandom random = new SecureRandom();
    byte[] challengeBytes = new byte[32];
    random.nextBytes(challengeBytes);
    return challengeBytes;
  }

  /**
   * Función encargada 
   * 
   * @param base64Url
   * @return
   */
  private String base64UrlToBase64(String base64Url) {
    return base64Url.replace("-", "+").replace("_", "/").replace("==", "");
  }

  /**
   * Función encargada de recibir una cadena string y convertila en byte[]
   * @param base64UrlEncoded
   * @return
   */
  private static byte[] convertirBase64UrlDecode(String base64UrlEncoded) {
    if (base64UrlEncoded == null || base64UrlEncoded.isEmpty()) {
      return new byte[0];
    }
    return Base64.getUrlDecoder().decode(base64UrlEncoded);
  }
   
  /**
   * Función encargada de crear un usuario en el caso de que no exista cuando se registra por passkeys
   * @param username
   * @return
   */
  private Usuario crearUsuario(String username) {
    byte[] randomBytes = new byte[16];
    new SecureRandom().nextBytes(randomBytes);
    return Usuario.builder().nombreUsuario(username)
        .password(bCryptPass.encode(Base64.getUrlEncoder().encodeToString(randomBytes)))
        .roles(Arrays.asList(roleService.findByNombreRol("USER"))).enabled(Boolean.TRUE).build();
  }
  
  /**
   * Función para almacenar al usu
   * @param usuario
   * @throws UsuarioException
   */
  private void guardarUsuario(Usuario usuario) throws PasskeyException {
    try {
      LOGGER.info("Se procede a guardar al usuario");
      usuariosService.guardarUsuario(usuario);
      LOGGER.info("Usuario guardado correctamente");
    } catch (Exception e) {
      throw new PasskeyException(e);
    }
  }
  
}
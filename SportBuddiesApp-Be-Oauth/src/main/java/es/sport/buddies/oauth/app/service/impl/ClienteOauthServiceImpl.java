package es.sport.buddies.oauth.app.service.impl;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.dto.ClientesOauthDto;
import es.sport.buddies.entity.app.models.entity.ClientesOauth;
import es.sport.buddies.entity.app.models.service.IClientesOauthService;

@Service
public class ClienteOauthServiceImpl implements RegisteredClientRepository {

  @Autowired
  private IClientesOauthService clientOauthService;
  
  @Override
  public void save(RegisteredClient registeredClient) {
    // No implementado ya que almaceno una Entidad distinta
  }
  
  public void guardar(ClientesOauthDto clientesOauth) {
    clientOauthService.guardarClienteOauth(clienteOauthDtoToEntity(clientesOauth));
  }
  
  @Override
  @Transactional(readOnly = true)
  public RegisteredClient findById(String id) {
    Optional<ClientesOauth>  cli = clientOauthService.findByClientId(id);
    if(cli.isPresent()) {
      return toRegisteredClient(cli.get());
    } 
    return null;
  }

  /**
   * Función que se llama cuando se consulta el servicio (en el caso del BE) '/oauth2/authorization/client-be'
   */
  @Override
  public RegisteredClient findByClientId(String clientId) {
    ClientesOauth cli = clientOauthService.findByClientId(clientId)
        .orElseThrow(() -> new RuntimeException("client not found"));
    return toRegisteredClient(cli);
  }

  private ClientesOauth clienteOauthDtoToEntity (ClientesOauthDto clientOauthDto) {
    ClientesOauth cli = new ClientesOauth();
    cli.setIdClienteOauth(clientOauthDto.getIdClienteOauth());
    cli.setClientId(clientOauthDto.getClientId());
    cli.setClientSecret(clientOauthDto.getClientSecret());
    cli.setClientName(clientOauthDto.getClientName());
    cli.setAuthenticationMethods(String.join(",", clientOauthDto.getAuthenticationMethods()));
    cli.setAuthorizationGrantTypes(String.join(",", clientOauthDto.getAuthorizationGrantTypes()));
    cli.setRedirectUris(String.join(",", clientOauthDto.getRedirectUris()));
    cli.setPostLogoutRedirectUris(String.join(",", clientOauthDto.getPostLogoutRedirectUris()));
    cli.setScopes(String.join(",", clientOauthDto.getScopes()));
    cli.setTimeAccesToken(clientOauthDto.getTimeAccesToken());
    cli.setTimeRefrehsToken(clientOauthDto.getTimeRefrehsToken());
    return cli;
  }
  
  /**
   * Función encargada de pasar de un ClientOauth de base de datos a un RegisteredClient para poder obtener el code y autenticar con la aplicación
   * @param client
   * @return
   */
  private RegisteredClient toRegisteredClient(ClientesOauth client){ 
    RegisteredClient.Builder builder = RegisteredClient.withId(client.getClientId())
            .clientId(client.getClientId())
            .clientSecret(client.getClientSecret())
            .clientName(client.getClientName())
            .clientIdIssuedAt(new Date().toInstant())
            .clientAuthenticationMethods(am -> am.addAll(Arrays.stream(client.getAuthenticationMethods().split(","))
                .map(ClientAuthenticationMethod::new).collect(Collectors.toSet())))
            .authorizationGrantTypes(auth -> auth.addAll(Arrays.stream(client.getAuthorizationGrantTypes().split(","))
                .map(AuthorizationGrantType::new).collect(Collectors.toSet())))
            .redirectUris(redirect -> redirect.addAll(Arrays.stream(client.getRedirectUris().split(","))
                .map(String::new).collect(Collectors.toSet())))
            .postLogoutRedirectUris(logout -> logout.addAll(Arrays.stream(client.getPostLogoutRedirectUris().split(","))
                .map(String::new).collect(Collectors.toSet())))
            .scopes(sc -> sc.addAll(Arrays.stream(client.getScopes().split(","))
                .map(String::new).collect(Collectors.toSet())))
            .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(client.getTimeAccesToken()))
                .refreshTokenTimeToLive(Duration.ofDays(client.getTimeRefrehsToken())).build())
            .clientSettings(ClientSettings
                    .builder()
                    .requireAuthorizationConsent(false)
                    .build());
    return builder.build();
  }
  
}

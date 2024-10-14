package es.sport.buddies.entity.app.models.service;

import java.util.Optional;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ClientesOauth;

public interface IClientesOauthService {

  public Optional<ClientesOauth> findByClientId(@Param("clientId") String clientId);
 
  public void guardarClienteOauth(ClientesOauth clientesOauth);
  
}

package es.sport.buddies.entity.app.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ClientesOauth;

public interface IClientesOauthDao extends JpaRepository<ClientesOauth, Long> {

  public Optional<ClientesOauth> findByClientId(@Param("clientId") String clientId);
  
  public List<ClientesOauth> findAll();
  
}

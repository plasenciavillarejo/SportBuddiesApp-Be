package es.sport.buddies.entity.app.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.sport.buddies.entity.app.models.entity.AutorizacionConsentimento;

public interface IAutorizacionConsentimentoDao extends JpaRepository<AutorizacionConsentimento, AutorizacionConsentimento.AuthorizationConsentId>{

  public Optional<AutorizacionConsentimento> findByIdClienteRegistradoAndPrincipalName(String registeredClientId, String principalName);

  public void deleteByIdClienteRegistradoAndPrincipalName(String registeredClientId, String principalName);

}

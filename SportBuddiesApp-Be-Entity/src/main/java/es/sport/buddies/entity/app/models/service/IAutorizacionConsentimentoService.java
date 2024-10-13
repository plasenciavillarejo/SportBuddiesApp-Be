package es.sport.buddies.entity.app.models.service;

import java.util.Optional;

import es.sport.buddies.entity.app.models.entity.AutorizacionConsentimento;

public interface IAutorizacionConsentimentoService {

  public Optional<AutorizacionConsentimento> findByIdClienteRegistradoAndPrincipalName(String registeredClientId, String principalName);

  public void deleteByIdClienteRegistradoAndPrincipalName(String registeredClientId, String principalName);

  public void guardarConsentimiento(AutorizacionConsentimento autorizacionConsentimento);
}

package es.sport.buddies.entity.app.models.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IAutorizacionConsentimentoDao;
import es.sport.buddies.entity.app.models.entity.AutorizacionConsentimento;
import es.sport.buddies.entity.app.models.service.IAutorizacionConsentimentoService;

@Service
public class AutorizacionConsentimentoServiceImpl implements IAutorizacionConsentimentoService {

  @Autowired
  private IAutorizacionConsentimentoDao consentimientoDao;

  @Override
  @Transactional(readOnly = true)
  public Optional<AutorizacionConsentimento> findByIdClienteRegistradoAndPrincipalName(String registeredClientId,
      String principalName) {
    return consentimientoDao.findByIdClienteRegistradoAndPrincipalName(registeredClientId, principalName);
  }

  @Override
  @Transactional
  public void deleteByIdClienteRegistradoAndPrincipalName(String registeredClientId, String principalName) {
    consentimientoDao.deleteByIdClienteRegistradoAndPrincipalName(registeredClientId, principalName);
  }

  @Override
  @Transactional
  public void guardarConsentimiento(AutorizacionConsentimento autorizacionConsentimento) {
    consentimientoDao.save(autorizacionConsentimento);
  }
  
}

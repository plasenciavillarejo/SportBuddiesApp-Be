package es.sport.buddies.entity.app.models.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IClientesOauthDao;
import es.sport.buddies.entity.app.models.entity.ClientesOauth;
import es.sport.buddies.entity.app.models.service.IClientesOauthService;

@Service
public class ClientesOauthServiceImpl implements IClientesOauthService {

  @Autowired
  private IClientesOauthDao clientesOauthDao;

  @Override
  @Transactional(readOnly = true)
  public Optional<ClientesOauth> findByClientId(String clientId) {
    return clientesOauthDao.findByClientId(clientId);
  }

  @Override
  @Transactional
  public void guardarClienteOauth(ClientesOauth clientesOauth) {
    clientesOauthDao.save(clientesOauth);
  }
  
}

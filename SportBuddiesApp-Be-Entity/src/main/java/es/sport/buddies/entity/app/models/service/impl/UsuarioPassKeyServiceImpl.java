package es.sport.buddies.entity.app.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IUsuarioPassKeyDao;
import es.sport.buddies.entity.app.models.entity.UsuarioPassKey;
import es.sport.buddies.entity.app.models.service.IUsuarioPassKeyService;

@Service
public class UsuarioPassKeyServiceImpl implements IUsuarioPassKeyService {

  @Autowired
  private IUsuarioPassKeyDao usuarioPasskeyDao;
  
  @Override
  @Transactional
  public void guardarUsuarioPasskeys(UsuarioPassKey usuarioPasskey) {
    usuarioPasskeyDao.save(usuarioPasskey);
  }

  @Override
  @Transactional(readOnly = true)
  public UsuarioPassKey findByCredencialId(String credentialId) {
    return usuarioPasskeyDao.findByCredencialId(credentialId);
  }

}

package es.sport.buddies.entity.app.models.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IUsuarioGoogleDao;
import es.sport.buddies.entity.app.models.entity.UsuarioGoogle;
import es.sport.buddies.entity.app.models.service.IUsuarioGoogleService;

@Service
public class UsuarioGoogleServiceImpl implements IUsuarioGoogleService {

  @Autowired
  private IUsuarioGoogleDao usuarioGoogleDao;

  @Override
  @Transactional(readOnly = true)
  public Optional<UsuarioGoogle> findByEmail(String email) {
    return usuarioGoogleDao.findByEmail(email);
  }

  @Override
  @Transactional
  public void guardarUsuarioGoogle(UsuarioGoogle usuarioGoogle) {
    usuarioGoogleDao.save(usuarioGoogle);
  }
  
  
  
}

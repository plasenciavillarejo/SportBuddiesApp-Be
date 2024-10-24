package es.sport.buddies.entity.app.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.models.dao.IUsuarioInRolDao;
import es.sport.buddies.entity.app.models.service.IUsuarioInRolService;

@Service
public class UsuarioInRolServiceImpl implements IUsuarioInRolService {

  @Autowired
  private IUsuarioInRolDao usuarioInRolDao;

}

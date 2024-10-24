package es.sport.buddies.entity.app.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IRoleDao;
import es.sport.buddies.entity.app.models.entity.Role;
import es.sport.buddies.entity.app.models.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

  @Autowired
  private IRoleDao roleDao;

  @Override
  @Transactional(readOnly = true)
  public Role findByNombreRol(String nombreRol) {
    return roleDao.findByNombreRol(nombreRol);
  }
  
}

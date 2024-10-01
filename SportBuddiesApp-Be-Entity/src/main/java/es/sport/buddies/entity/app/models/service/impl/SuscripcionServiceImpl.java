package es.sport.buddies.entity.app.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.ISuscripcionDao;
import es.sport.buddies.entity.app.models.entity.Suscripcion;
import es.sport.buddies.entity.app.models.service.ISuscripcionService;

@Service
public class SuscripcionServiceImpl implements ISuscripcionService {

  @Autowired
  private ISuscripcionDao suscripcionDao;

  @Override
  @Transactional(readOnly = true)
  public Suscripcion findByUsuario_IdUsuario(long idUsuario) {
    return suscripcionDao.findByUsuario_IdUsuario(idUsuario);
  }
  
}

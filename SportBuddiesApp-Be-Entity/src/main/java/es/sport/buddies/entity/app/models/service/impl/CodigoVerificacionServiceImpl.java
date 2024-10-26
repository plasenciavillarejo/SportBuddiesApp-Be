package es.sport.buddies.entity.app.models.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.ICodigoVerificacionDao;
import es.sport.buddies.entity.app.models.entity.CodigoVerificacion;
import es.sport.buddies.entity.app.models.service.ICodigoVerificacionService;

@Service
public class CodigoVerificacionServiceImpl implements ICodigoVerificacionService {

  @Autowired
  private ICodigoVerificacionDao codigoVerificacionDao;
  
  @Override
  @Transactional
  public void guardarCodigoVerificacion(CodigoVerificacion codigoVerificacion) {
    codigoVerificacionDao.save(codigoVerificacion);
  }

  @Override
  @Transactional(readOnly = true)
  public CodigoVerificacion findByUsuario_IdUsuario(long idUsuario) {
    return codigoVerificacionDao.findByUsuario_IdUsuario(idUsuario);
  }

  @Override
  @Transactional
  public void actualizarTiempoExpiracion(LocalDateTime expiracion, long idUsuario, String codigo) {
    codigoVerificacionDao.actualizarTiempoExpiracion(expiracion, idUsuario, codigo);
  }

}

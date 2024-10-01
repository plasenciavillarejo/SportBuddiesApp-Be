package es.sport.buddies.entity.app.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IUsuarioPlanPagoDao;
import es.sport.buddies.entity.app.models.entity.UsuarioPlanPago;
import es.sport.buddies.entity.app.models.service.IUsuarioPlanPagoService;

@Service
public class UsuarioPlanPagoServiceImpl implements IUsuarioPlanPagoService {

  @Autowired
  private IUsuarioPlanPagoDao usuarioPlanPagoDao;

  @Override
  @Transactional(readOnly = true)
  public UsuarioPlanPago findBySuscripcion_IdSuscripcion(long idSuscripcion) {
    return usuarioPlanPagoDao.findBySuscripcion_IdSuscripcion(idSuscripcion);
  }

  @Override
  @Transactional
  public void actualizarReservasRestantes(long reservasRestantes, long idPlanPago) {
    usuarioPlanPagoDao.actualizarReservasRestantes(reservasRestantes, idPlanPago);
  }
  
}

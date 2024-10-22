package es.sport.buddies.entity.app.models.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IPaypalDao;
import es.sport.buddies.entity.app.models.entity.Paypal;
import es.sport.buddies.entity.app.models.service.IPaypalService;

@Service
public class PaypalServiceImpl implements IPaypalService {

  @Autowired
  private IPaypalDao paypalDao;

  @Override
  @Transactional
  public void guardar(Paypal paypal) {
    paypalDao.save(paypal);    
  }

  @Override
  @Transactional(readOnly = true)
  public Paypal buscarReservaPagada(long idReservaUsuario) {
    return paypalDao.buscarReservaPagada(idReservaUsuario);
  }

  @Override
  @Transactional
  public void actualizarReservaReembolsada(LocalDate fechaReembolso, long idPaypal) {
    paypalDao.actualizarReservaReembolsada(fechaReembolso, idPaypal);
  }

}

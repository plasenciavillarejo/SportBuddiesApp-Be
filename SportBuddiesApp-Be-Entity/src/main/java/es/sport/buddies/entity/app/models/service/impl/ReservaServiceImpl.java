package es.sport.buddies.entity.app.models.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IReservaDao;
import es.sport.buddies.entity.app.models.entity.Reserva;
import es.sport.buddies.entity.app.models.service.IReservaService;

@Service
public class ReservaServiceImpl implements IReservaService {

  @Autowired
  private IReservaDao reservaDao;

  @Override
  @Transactional(readOnly = true)
  public List<Reserva> buscarReservaPorFechaAndIdUsuario(LocalDate fechaReserva) {
    return reservaDao.buscarReservaPorFechaAndIdUsuario(fechaReserva);
  }
  
}

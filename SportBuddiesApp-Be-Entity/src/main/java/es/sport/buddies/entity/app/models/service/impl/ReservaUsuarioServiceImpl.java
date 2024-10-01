package es.sport.buddies.entity.app.models.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IReservaUsuarioDao;
import es.sport.buddies.entity.app.models.entity.ReservaUsuario;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;

@Service
public class ReservaUsuarioServiceImpl implements IReservaUsuarioService {

  @Autowired
  private IReservaUsuarioDao reservaDao;

  @Override
  @Transactional(readOnly = true)
  public List<ReservaUsuario> buscarReservaPorFechaAndIdUsuario(LocalDate fechaReserva, long idReserva) {
    return reservaDao.buscarReservaPorFechaAndIdUsuario(fechaReserva, idReserva);
  }

  @Override
  @Transactional
  public void guardarReservaUsuario(ReservaUsuario reservaUsuario) {
    reservaDao.save(reservaUsuario);
  }

  @Override
  @Transactional(readOnly = true)
  public ReservaUsuario findByUsuario_IdUsuarioAndReservaActividad_IdReservaActividad(long idUsuario, long idReserva) {
    return reservaDao.findByUsuario_IdUsuarioAndReservaActividad_IdReservaActividad(idUsuario, idReserva);
  }
  
}

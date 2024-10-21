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
  private IReservaUsuarioDao reservaUsuarioDao;

  @Override
  @Transactional(readOnly = true)
  public List<ReservaUsuario> buscarReservaPorFechaAndIdUsuario(LocalDate fechaReserva, long idUsuario, boolean historial) {
    return reservaUsuarioDao.buscarReservaPorFechaAndIdUsuario(fechaReserva, idUsuario, historial);
  }

  @Override
  @Transactional
  public void guardarReservaUsuario(ReservaUsuario reservaUsuario) {
    reservaUsuarioDao.save(reservaUsuario);
  }

  @Override
  @Transactional(readOnly = true)
  public ReservaUsuario findByUsuario_IdUsuarioAndReservaActividad_IdReservaActividad(long idUsuario, long idReserva) {
    return reservaUsuarioDao.findByUsuario_IdUsuarioAndReservaActividad_IdReservaActividad(idUsuario, idReserva);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Long> obtenerReservasPorUsuario(long idUsuario) {
    return reservaUsuarioDao.obtenerReservasPorUsuario(idUsuario);
  }

  @Override
  @Transactional
  public void borrarReservaActividad(long idUsuario, long idReserva) {
    reservaUsuarioDao.borrarReservaActividad(idUsuario, idReserva);
  }

  @Override
  @Transactional
  public void actualizarAbonoReserva(long idReserva) {
    reservaUsuarioDao.actualizarAbonoReserva(idReserva);
  }

  @Override
  @Transactional(readOnly = true)
  public ReservaUsuario validarAbonoReserva(long idUsuario, long idReserva) {
    return reservaUsuarioDao.validarAbonoReserva(idUsuario, idReserva);
  }
  

}

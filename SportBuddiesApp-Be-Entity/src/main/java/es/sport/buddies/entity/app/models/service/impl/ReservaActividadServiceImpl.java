package es.sport.buddies.entity.app.models.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IReservaActividadDao;
import es.sport.buddies.entity.app.models.entity.ReservaActividad;
import es.sport.buddies.entity.app.models.service.IReservaActividadService;

@Service
public class ReservaActividadServiceImpl implements IReservaActividadService {

  @Autowired
  private IReservaActividadDao reservaActividadDao;
  
  @Override
  @Transactional(readOnly = true)
  public Page<ReservaActividad> findByFechaReservaAndActividadAndProvinciaAndMunicipioAndUsuarioActividad_IdUsuarioNot(
      Date fechaReserva, String actividad, String provincia, String municipio, long idUsuario, Pageable pageable) {
    return reservaActividadDao.findByFechaReservaAndActividadAndProvinciaAndMunicipioAndUsuarioActividad_IdUsuarioNot(fechaReserva, actividad, provincia, municipio,
        idUsuario, pageable);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Page<ReservaActividad> findByFechaReservaAndActividadAndProvinciaAndMunicipio(Date fechaReserva,
      String actividad, String provincia, String municipio, Pageable pageable) {
    return reservaActividadDao.findByFechaReservaAndActividadAndProvinciaAndMunicipio(fechaReserva, actividad, provincia, municipio,pageable);
  }

  @Override
  @Transactional
  public void guardarReservaActividad(ReservaActividad reservaActividad) {
    reservaActividadDao.save(reservaActividad);
  }

  @Override
  @Transactional(readOnly = true)
  public ReservaActividad findByProvinciaAndMunicipioAndFechaReservaAndHoraInicioAndHoraFinAndUsuarioActividad_IdUsuario(
      String provincia, String municpio, LocalDate fechaReserva, LocalTime horaInicio, LocalTime horaFin,
      long idUsuario) {
    return reservaActividadDao
        .findByProvinciaAndMunicipioAndFechaReservaAndHoraInicioAndHoraFinAndUsuarioActividad_IdUsuario(provincia,
            municpio, fechaReserva, horaInicio, horaFin, idUsuario);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ReservaActividad> findByUsuarioActividad_IdUsuario(long idUsuario,Pageable pageable) {
    return reservaActividadDao.findByUsuarioActividad_IdUsuario(idUsuario,pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public ReservaActividad findById(long idReservaActividad) {
    return reservaActividadDao.findById(idReservaActividad).orElse(null);
  }

  @Override
  @Transactional
  public void actualizarPlazaRestantes(long idReservaActividad, long plazaRestante) {
    reservaActividadDao.actualizarPlazaRestantes(idReservaActividad, plazaRestante);
  }
  
}

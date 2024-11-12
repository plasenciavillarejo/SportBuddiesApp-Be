package es.sport.buddies.entity.app.models.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
  public List<ReservaActividad> findByFechaReservaAndActividadAndProvinciaAndMunicipio(Date fechaReserva,
      String actividad, String provincia, String municipio) {
    return reservaActividadDao.findByFechaReservaAndActividadAndProvinciaAndMunicipio(fechaReserva, actividad, provincia, municipio);
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
  public List<ReservaActividad> findByUsuarioActividad_IdUsuario(long idUsuario) {
    return reservaActividadDao.findByUsuarioActividad_IdUsuario(idUsuario);
  }
  
}

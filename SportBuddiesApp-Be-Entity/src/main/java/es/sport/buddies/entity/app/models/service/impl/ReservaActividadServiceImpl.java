package es.sport.buddies.entity.app.models.service.impl;

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
  
  
  
  
}

package es.sport.buddies.entity.app.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.models.dao.IReservaActividadDao;
import es.sport.buddies.entity.app.models.service.IReservaActividadService;

@Service
public class ReservaActividadServiceImpl implements IReservaActividadService {

  @Autowired
  private IReservaActividadDao reservaActividadDao;
  
  
  
  
}

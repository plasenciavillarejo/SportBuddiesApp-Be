package es.sport.buddies.entity.app.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.models.dao.ITipoEstadoDao;
import es.sport.buddies.entity.app.models.service.ITipoEstadoService;

@Service
public class TipoEstadoServiceImpl implements ITipoEstadoService {

  @Autowired
  private ITipoEstadoDao tipoEstadoDao;
  
}

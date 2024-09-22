package es.sport.buddies.entity.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IMunicipioDao;
import es.sport.buddies.entity.app.models.entity.Municipio;
import es.sport.buddies.entity.app.models.service.IMunicipioService;

@Service
public class MunicipioServiceImpl implements IMunicipioService {

  @Autowired
  private IMunicipioDao municipioDao;

  @Override
  @Transactional(readOnly = true)
  public List<Municipio> findByProvincia_NombreProvincia(String nombreProvincia) {
    return municipioDao.findByProvincia_NombreProvincia(nombreProvincia);
  }
  
}

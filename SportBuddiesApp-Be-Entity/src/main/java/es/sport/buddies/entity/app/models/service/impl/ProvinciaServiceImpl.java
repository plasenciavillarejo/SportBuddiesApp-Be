package es.sport.buddies.entity.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IProvinciaDao;
import es.sport.buddies.entity.app.models.service.IProvinciaService;

@Service
public class ProvinciaServiceImpl implements IProvinciaService {

  @Autowired
  private IProvinciaDao provinciaDao;
  
  @Override
  @Transactional(readOnly = true)
  public List<String> listarProvincias() {
    return provinciaDao.findAll().stream().map(provin -> provin.getNombreProvincia()).toList();
  }

}

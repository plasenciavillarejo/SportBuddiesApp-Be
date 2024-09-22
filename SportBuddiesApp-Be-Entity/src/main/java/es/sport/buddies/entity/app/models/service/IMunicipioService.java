package es.sport.buddies.entity.app.models.service;

import java.util.List;

import es.sport.buddies.entity.app.models.entity.Municipio;

public interface IMunicipioService {

  public List<Municipio> findByProvincia_NombreProvincia(String nombreProvincia);
}

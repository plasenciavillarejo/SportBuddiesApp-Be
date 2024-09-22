package es.sport.buddies.entity.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.sport.buddies.entity.app.models.entity.Municipio;

public interface IMunicipioDao extends JpaRepository<Municipio, Long> {

  public List<Municipio> findByProvincia_NombreProvincia(String nombreProvincia);
  
}

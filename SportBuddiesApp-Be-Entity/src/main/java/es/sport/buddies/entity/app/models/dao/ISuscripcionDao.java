package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.Suscripcion;

public interface ISuscripcionDao extends JpaRepository<Suscripcion, Long> {

  public Suscripcion findByUsuario_IdUsuario(@Param("idUsuario") long idUsuario); 
  
}

package es.sport.buddies.entity.app.models.service;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.Suscripcion;

public interface ISuscripcionService {

  public Suscripcion findByUsuario_IdUsuario(@Param("idUsuario") long idUsuario);
  
  public void guardarSuscripcion(Suscripcion suscripcion);
  
}

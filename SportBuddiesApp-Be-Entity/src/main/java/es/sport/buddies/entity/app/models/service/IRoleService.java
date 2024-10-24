package es.sport.buddies.entity.app.models.service;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.Role;

public interface IRoleService {

  public Role findByNombreRol(@Param("nombreRol") String nombreRol);
  
}

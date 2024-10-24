package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.Role;

public interface IRoleDao extends JpaRepository<Role, Long> {

  public Role findByNombreRol(@Param("nombreRol") String nombreRol); 
  
}

package es.sport.buddies.entity.app.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.sport.buddies.entity.app.models.entity.UsuarioGoogle;

public interface IUsuarioGoogleDao extends JpaRepository<UsuarioGoogle, Long>{

  public Optional<UsuarioGoogle> findByEmail(String email);
  
}

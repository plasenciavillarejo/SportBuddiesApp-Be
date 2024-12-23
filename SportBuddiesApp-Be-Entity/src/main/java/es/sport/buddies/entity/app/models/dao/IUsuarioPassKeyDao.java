package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import es.sport.buddies.entity.app.models.entity.UsuarioPassKey;

public interface IUsuarioPassKeyDao extends JpaRepository<UsuarioPassKey, Long> {

  public UsuarioPassKey findByCredencialId(String credentialId);
  
}

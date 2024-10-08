package es.sport.buddies.entity.app.models.service;

import java.util.Optional;

import es.sport.buddies.entity.app.models.entity.UsuarioGoogle;

public interface IUsuarioGoogleService {

  public Optional<UsuarioGoogle> findByEmail(String email);
  
  public void guardarUsuarioGoogle(UsuarioGoogle usuarioGoogle);
  
}

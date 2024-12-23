package es.sport.buddies.entity.app.models.service;

import es.sport.buddies.entity.app.models.entity.UsuarioPassKey;

public interface IUsuarioPassKeyService {

  public void guardarUsuarioPasskeys(UsuarioPassKey usuarioPasskey);
  
  public UsuarioPassKey findByCredencialId(String credentialId);
  
}

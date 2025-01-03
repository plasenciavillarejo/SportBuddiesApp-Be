package es.sport.buddies.main.app.service;

import es.sport.buddies.entity.app.dto.UsuarioDto;
import es.sport.buddies.main.app.exceptions.UsuarioException;

public interface IUsuarioMainService {

  public void crearNuevoUsuario(UsuarioDto usuarioDto) throws UsuarioException;
 
  public void actualizarUsuario(UsuarioDto usuarioDto) throws UsuarioException;
  
  public UsuarioDto localizarUsuario(long idUsuario) throws UsuarioException;
  
}

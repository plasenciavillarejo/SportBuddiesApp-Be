package es.sport.buddies.entity.app.models.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ConfirmacionUsuario;

public interface IConfirmacionUsuarioService {

  public void guardarConfirmacionUsuario(ConfirmacionUsuario confirmacionUsuario);
  
  public List<Long> listarIdsUsuariosConfirmados(@Param("idUsuario") long idUsuario);
  
}

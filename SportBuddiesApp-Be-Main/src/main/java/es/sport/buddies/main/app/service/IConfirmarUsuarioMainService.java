package es.sport.buddies.main.app.service;

import java.util.List;
import java.util.Map;

import es.sport.buddies.entity.app.dto.ConfirmarUsuarioDto;
import es.sport.buddies.entity.app.dto.UsuariosConfirmadosDto;
import es.sport.buddies.main.app.exceptions.ConfirmarAsistenciaException;

public interface IConfirmarUsuarioMainService {

  public Map<String, Object> listarUsuariosConfirmados(ConfirmarUsuarioDto confirmarAsistenciaDto) throws ConfirmarAsistenciaException;
 
  public void almacenarConfirmacionUsuario(ConfirmarUsuarioDto confirmacionAsistenciaDto) throws ConfirmarAsistenciaException;
  
  public List<UsuariosConfirmadosDto> listIdConfirmados(long idUsuario);
  
}

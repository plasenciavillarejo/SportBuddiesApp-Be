package es.sport.buddies.main.app.service;

import java.util.List;
import java.util.Map;

import es.sport.buddies.entity.app.dto.ConfirmarAsistenciaDto;
import es.sport.buddies.main.app.exceptions.ConfirmarAsistenciaException;

public interface IConfirmarAsistenciaMainService {

  public Map<String, Object> listarUsuariosConfirmados(ConfirmarAsistenciaDto confirmarAsistenciaDto) throws ConfirmarAsistenciaException;
 
  public void almacenarConfirmacionUsuario(ConfirmarAsistenciaDto confirmacionAsistenciaDto) throws ConfirmarAsistenciaException;
  
  public List<Long> listIdConfirmados(long idUsuario);
  
}

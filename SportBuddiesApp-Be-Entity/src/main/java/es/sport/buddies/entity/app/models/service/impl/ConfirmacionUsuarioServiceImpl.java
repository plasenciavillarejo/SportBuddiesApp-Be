package es.sport.buddies.entity.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IConfirmacionUsuarioDao;
import es.sport.buddies.entity.app.models.entity.ConfirmacionUsuario;
import es.sport.buddies.entity.app.models.service.IConfirmacionUsuarioService;

@Service
public class ConfirmacionUsuarioServiceImpl implements IConfirmacionUsuarioService {
  
  @Autowired
  private IConfirmacionUsuarioDao confirmacionUsuarioDao;

  @Override
  @Transactional
  public void guardarConfirmacionUsuario(ConfirmacionUsuario confirmacionUsuario) {
    confirmacionUsuarioDao.save(confirmacionUsuario);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Object[]> listarIdsUsuariosConfirmados(long idUsuario) {
    return confirmacionUsuarioDao.listarIdsUsuariosConfirmados(idUsuario);
  }

}

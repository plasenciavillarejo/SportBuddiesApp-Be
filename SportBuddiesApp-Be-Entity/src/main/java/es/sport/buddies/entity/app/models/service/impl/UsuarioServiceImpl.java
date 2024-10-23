package es.sport.buddies.entity.app.models.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IUsuariosDao;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuariosDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> findById(Long idUsuario) {
		return usuarioDao.findById(idUsuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByNombreUsuario(String nombre) {
		return usuarioDao.findByNombreUsuario(nombre);
	}

  @Override
  @Transactional(readOnly = true)
  public Usuario findByEmail(String email) {
    return usuarioDao.findByEmail(email);
  }

  @Override
  @Transactional
  public void guardarUsuario(Usuario usuario) {
    usuarioDao.save(usuario);
  }

}

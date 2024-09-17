package es.sport.buddies.entity.app.models.service;

import java.util.Optional;

import es.sport.buddies.entity.app.models.entity.Usuario;

public interface IUsuarioService {

	public Optional<Usuario> findById(Long idUsuario);
	
	public Usuario findByNombreUsuario(String nombre);
	
}

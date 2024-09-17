package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.sport.buddies.entity.app.models.entity.Usuario;

public interface IUsuariosDao extends JpaRepository<Usuario, Long> {

  @Query("SELECT u FROM Usuario u JOIN FETCH u.roles WHERE u.nombreUsuario = :nombre")
	public Usuario buscarUsuarioConRoles(String nombre);
	
}

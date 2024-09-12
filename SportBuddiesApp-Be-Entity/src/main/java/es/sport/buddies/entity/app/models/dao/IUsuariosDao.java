package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import es.sport.buddies.entity.app.models.entity.Usuario;

public interface IUsuariosDao extends JpaRepository<Usuario, Long> {

	public Usuario findByNombre(String nombre);
}

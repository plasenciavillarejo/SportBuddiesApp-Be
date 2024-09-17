package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import es.sport.buddies.entity.app.models.entity.Usuario;

public interface IUsuariosDao extends JpaRepository<Usuario, Long> {

  // @EntityGraph: Permite definir qué relaciones se deben cargar de manera anticipada cuando haces una consulta
  @EntityGraph(attributePaths = {"roles"})
  //@Query("SELECT u FROM Usuario u JOIN FETCH u.roles WHERE u.nombreUsuario = :nombre")
	public Usuario findByNombreUsuario(String nombre);
	
}

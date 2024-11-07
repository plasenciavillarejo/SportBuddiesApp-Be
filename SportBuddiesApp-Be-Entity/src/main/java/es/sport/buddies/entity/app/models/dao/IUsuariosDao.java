package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.Usuario;

public interface IUsuariosDao extends JpaRepository<Usuario, Long> {

  // @EntityGraph: Permite definir qué relaciones se deben cargar de manera anticipada cuando haces una consulta
  @EntityGraph(attributePaths = {"roles"})
  /* Para hacer lo mismo sería lanzando esta query, pero al incluir el EntityGraph ya lo hace JPA internamente
    @Query("SELECT u FROM Usuario u JOIN FETCH u.roles WHERE u.nombreUsuario = :nombre") */
	public Usuario findByNombreUsuario(String nombre);
	
  public Usuario findByEmail(@Param("email") String email);
  
  @Modifying
  @Query(value = "update Usuario set email= :email, direccion = :direccion, provincia = :provincia, municipio = :municipio,"
      + " codigoPostal = :codigoPostal, pais = :pais, numeroTelefono = :numeroTelefono where idUsuario = :idUsuario")
  public void actualizarUsuario(@Param("email") String email, @Param("direccion") String direccion, @Param("provincia") String provincia,
      @Param("municipio") String municipio,@Param("codigoPostal") String codigoPostal, @Param("pais") String pais,
      @Param("numeroTelefono") String numeroTelefono, @Param("idUsuario") long idUsuario);
  
}

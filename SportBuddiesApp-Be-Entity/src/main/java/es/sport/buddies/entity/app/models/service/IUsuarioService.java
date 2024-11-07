package es.sport.buddies.entity.app.models.service;

import java.util.Optional;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.Usuario;

public interface IUsuarioService {

	public Optional<Usuario> findById(Long idUsuario);
	
	public Usuario findByNombreUsuario(String nombre);
	
	public Usuario findByEmail(@Param("email") String email);
	
	public void guardarUsuario(Usuario usuario);
	
  public void actualizarUsuario(@Param("direccion") String direccion, @Param("provincia") String provincia, @Param("municipio") String municipio,
      @Param("codigoPostal") String codigoPostal, @Param("pais") String pais, @Param("numeroTelefono") String numeroTelefono,
      @Param("idUsuario") long idUsuario);
	
}

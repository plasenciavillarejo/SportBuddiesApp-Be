package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

public class UsuarioDto implements Serializable {

	private String idUsuario;

	private String nombre;

	private String descripcion;

	private static final long serialVersionUID = -5083569157036242898L;

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}

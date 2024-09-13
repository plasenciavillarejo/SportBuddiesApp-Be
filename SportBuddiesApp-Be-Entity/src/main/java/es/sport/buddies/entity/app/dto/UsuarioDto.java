package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

public class UsuarioDto implements Serializable {

  private String idUsuario;

  private String nombreUsuario;

  private String descripcion;

  private static final long serialVersionUID = -5083569157036242898L;

  public String getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}

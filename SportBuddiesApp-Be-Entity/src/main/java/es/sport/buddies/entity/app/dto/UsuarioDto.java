package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

public class UsuarioDto implements Serializable {

  private long idUsuario;

  private String nombreUsuario;

  private static final long serialVersionUID = -5083569157036242898L;

  public long getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(long idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

}

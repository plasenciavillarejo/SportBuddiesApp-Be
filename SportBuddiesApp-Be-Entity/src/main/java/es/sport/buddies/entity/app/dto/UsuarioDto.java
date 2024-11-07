package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UsuarioDto implements Serializable {

  private long idUsuario;

  private String nombreUsuario;

  private String email;
  
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Solo se permite la escritura
  private String password;
  
  private String direccion;
  
  private String provincia;
  
  private String municipio;
  
  private String codigoPostal;
  
  private String pais;
  
  private String numeroTelefono;
  
  private static final long serialVersionUID = -5083569157036242898L;

}

package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AccesoDenegadoDto implements Serializable {

  private String message;

  private int status;

  private static final long serialVersionUID = -8385635931732523292L;

}

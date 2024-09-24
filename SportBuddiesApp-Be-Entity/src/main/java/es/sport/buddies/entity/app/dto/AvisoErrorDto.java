package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvisoErrorDto implements Serializable {

  private Date localDate;

  private int codigo;

  private String descripcion;

  private String mensaje;

  private String causa;

  private String stack;

  private Map<String, String> errores;

  private static final long serialVersionUID = -4137497035886619025L;

}

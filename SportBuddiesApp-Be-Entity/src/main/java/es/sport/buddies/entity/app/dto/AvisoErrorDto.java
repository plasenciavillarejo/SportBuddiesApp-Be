package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class AvisoErrorDto implements Serializable {

  private Date localDate;

  private int codigo;

  private String descripcion;

  private String mensaje;

  private String causa;

  private String stack;

  private Map<String, String> errores;

  private static final long serialVersionUID = -4137497035886619025L;

  public AvisoErrorDto(Date localDate, int codigo, String mensaje, String causa, String stack) {
    super();
    this.localDate = localDate;
    this.codigo = codigo;
    this.mensaje = mensaje;
    this.causa = causa;
    this.stack = stack;
  }

  
  
}

package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class AvisoErrorDto implements Serializable {

  private Date localDate;

  private int codigo;

  private String descripcion;

  private String mensaje;

  private String causa;

  private String stack;

  private Map<String, String> errores;

  public Date getLocalDate() {
    return localDate;
  }

  public void setLocalDate(Date localDate) {
    this.localDate = localDate;
  }

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public String getCausa() {
    return causa;
  }

  public void setCausa(String causa) {
    this.causa = causa;
  }

  public String getStack() {
    return stack;
  }

  public void setStack(String stack) {
    this.stack = stack;
  }

  public Map<String, String> getErrores() {
    return errores;
  }

  public void setErrores(Map<String, String> errores) {
    this.errores = errores;
  }

  public AvisoErrorDto() {
    super();
  }

  public AvisoErrorDto(Date localDate, int codigo, String descripcion, String causa, String stack) {
    super();
    this.localDate = localDate;
    this.codigo = codigo;
    this.descripcion = descripcion;
    this.causa = causa;
    this.stack = stack;
  }

  private static final long serialVersionUID = -4137497035886619025L;

}

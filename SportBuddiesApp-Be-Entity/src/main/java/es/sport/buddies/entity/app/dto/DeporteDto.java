package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

public class DeporteDto implements Serializable {

  private long idDeporte;

  private String actividad;

  public long getIdDeporte() {
    return idDeporte;
  }

  public void setIdDeporte(long idDeporte) {
    this.idDeporte = idDeporte;
  }

  public String getActividad() {
    return actividad;
  }

  public void setActividad(String actividad) {
    this.actividad = actividad;
  }

  private static final long serialVersionUID = -6165008468403280269L;

}

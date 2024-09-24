package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeporteDto implements Serializable {

  private long idDeporte;

  private String actividad;

  private static final long serialVersionUID = -6165008468403280269L;

}

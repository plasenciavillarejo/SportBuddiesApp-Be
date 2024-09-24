package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class DeporteDto implements Serializable {

  private long idDeporte;

  private String actividad;

  private static final long serialVersionUID = -6165008468403280269L;

}

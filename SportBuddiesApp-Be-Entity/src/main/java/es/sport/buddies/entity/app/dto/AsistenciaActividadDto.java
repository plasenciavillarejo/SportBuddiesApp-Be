package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AsistenciaActividadDto implements Serializable {
  
  private long idUsuario;
  
  private CaracteristicasPaginacionDto caracteristicasPaginacion;
  
  private static final long serialVersionUID = -7548542267409530201L;

}
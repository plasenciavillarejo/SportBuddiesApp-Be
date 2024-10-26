package es.sport.buddies.entity.app.models.service;

import java.time.LocalDateTime;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.CodigoVerificacion;

public interface ICodigoVerificacionService {

  public void guardarCodigoVerificacion(CodigoVerificacion codigoVerificacion);
  
  public CodigoVerificacion findByUsuario_IdUsuario(@Param("idUsuario") long idUsuario);
  
  public void actualizarTiempoExpiracion(@Param("expiracion") LocalDateTime expiracion, 
      @Param("idUsuario") long idUsuario, @Param("codigo") String codigo);
  
}

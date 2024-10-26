package es.sport.buddies.entity.app.models.dao;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.CodigoVerificacion;

public interface ICodigoVerificacionDao extends JpaRepository<CodigoVerificacion, Long> {

  public CodigoVerificacion findByUsuario_IdUsuario(@Param("idUsuario") long idUsuario);
  
  @Modifying
  @Query(value = "update CodigoVerificacion cv set cv.tiempoExpiracion = :expiracion, cv.codigo = :codigo"
      + " where cv.usuario.idUsuario = :idUsuario")
  public void actualizarTiempoExpiracion(@Param("expiracion") LocalDateTime expiracion, 
      @Param("idUsuario") long idUsuario, @Param("codigo") String codigo);
  
}

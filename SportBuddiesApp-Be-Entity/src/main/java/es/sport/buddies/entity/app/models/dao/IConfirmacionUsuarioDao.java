package es.sport.buddies.entity.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ConfirmacionUsuario;

public interface IConfirmacionUsuarioDao extends JpaRepository<ConfirmacionUsuario, Long> {

  @Query(value = "select usuario_fk from confirmacion_usuarios confUsu"
      + " inner join reservas_actividad resAct"
      + " on confUsu.reserva_actividad_fk = resAct.id_reserva_actividad"
      + " where resAct.usuario_actividad_fk = :idUsuario", nativeQuery = true)
  public List<Long> listarIdsUsuariosConfirmados(@Param("idUsuario") long idUsuario);
  
}

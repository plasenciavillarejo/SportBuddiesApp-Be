package es.sport.buddies.entity.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ConfirmacionUsuario;

public interface IConfirmacionUsuarioDao extends JpaRepository<ConfirmacionUsuario, Long> {

  @NativeQuery(value = "select confUsu.usuario_fk, confUsu.fecha_reserva, confUsu.hora_inicio, confUsu.hora_fin,"
      + " resAct.actividad"
      + " FROM confirmacion_usuarios confUsu"
      + " INNER JOIN reservas_actividad resAct"
      + " ON confUsu.reserva_actividad_fk = resAct.id_reserva_actividad"
      + " WHERE resAct.usuario_actividad_fk = :idUsuario"
        + " AND confUsu.fecha_reserva = resAct.fecha_reserva"
        + " AND SUBSTRING(confUsu.hora_inicio, 1, 5) = SUBSTRING(resAct.hora_inicio, 1, 5)"
        + " AND SUBSTRING(confUsu.hora_fin, 1, 5) = SUBSTRING(resAct.hora_fin, 1, 5)"
        + " AND resAct.fecha_reserva >= CURRENT_DATE")
  public List<Object[]> listarIdsUsuariosConfirmados(@Param("idUsuario") long idUsuario);
  
}

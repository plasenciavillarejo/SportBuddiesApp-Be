package es.sport.buddies.entity.app.models.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ReservaUsuario;


public interface IReservaUsuarioDao extends JpaRepository<ReservaUsuario, Long> {
  @Query(value = "from ReservaUsuario reserva"
      + " left join fetch reserva.usuario usuReserva"
      + " left join fetch reserva.deporte deportReserva "
      + " where usuReserva.idUsuario = :idUsuario"
      + " and ("
      + "   (:historial = true AND ("
      + "     (:fechaReserva IS NULL AND reserva.fechaReserva <= CURRENT_DATE)"
      + "       or (reserva.fechaReserva = :fechaReserva AND :fechaReserva <= CURRENT_DATE)"
      + "   ))"
      + " or "
      + "   (:historial = false AND ("
      + "     (:fechaReserva is null and reserva.fechaReserva >= CURRENT_DATE)"
      + "       or (:fechaReserva is not null and reserva.fechaReserva = :fechaReserva AND :fechaReserva >= CURRENT_DATE)"
      + "   ))"
      + " )")
  public List<ReservaUsuario> buscarReservaPorFechaAndIdUsuario(@Param("fechaReserva") LocalDate fechaReserva, @Param("idUsuario") long idUsuario, 
      @Param("historial") boolean historial);
 
  public ReservaUsuario findByUsuario_IdUsuarioAndReservaActividad_IdReservaActividad(@Param("idUsuario") long idUsuario, @Param("idReserva") long idReserva);
  
  @EntityGraph(attributePaths = {"reservaActividad"})
  @Query("SELECT r.reservaActividad.idReservaActividad FROM ReservaUsuario r WHERE r.usuario.idUsuario = :idUsuario")
  public List<Long> obtenerReservasPorUsuario(@Param("idUsuario") long idUsuario);
  
  @Modifying
  @Query(value = "delete from ReservaUsuario resUsu where resUsu.usuario.idUsuario= :idUsuario and resUsu.reservaActividad.idReservaActividad = :idReserva")
  public void borrarReservaActividad(@Param("idUsuario") long idUsuario, @Param("idReserva") long idReserva);
 
  @Modifying
  @Query(value = "update ReservaUsuario set abonado = true where idReserva = :idReserva")
  public void actualizarAbonoReserva(@Param("idReserva") long idReserva);
  
}

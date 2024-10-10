package es.sport.buddies.entity.app.models.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ReservaUsuario;

public interface IReservaUsuarioDao extends JpaRepository<ReservaUsuario, Long> {

  @Query(value =" select reserva from ReservaUsuario reserva"
      + " left join fetch reserva.usuario usuReserva"
      + " left join fetch reserva.deporte deportReserva "
      + " where usuReserva.idUsuario = :idUsuario"
      + " and (:fechaReserva IS NULL or reserva.fechaReserva = :fechaReserva)")
  public List<ReservaUsuario> buscarReservaPorFechaAndIdUsuario(@Param("fechaReserva") LocalDate fechaReserva, @Param("idUsuario") long idUsuario);
 
  public ReservaUsuario findByUsuario_IdUsuarioAndReservaActividad_IdReservaActividad(@Param("idUsuario") long idUsuario, @Param("idReserva") long idReserva);
  
  @Query("SELECT r.idReserva FROM ReservaUsuario r WHERE r.usuario.idUsuario = :idUsuario")
  List<Long> obtenerReservasPorUsuario(@Param("idUsuario") long idUsuario);
  
}

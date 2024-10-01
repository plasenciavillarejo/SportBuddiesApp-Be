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
      + " where reserva.idReserva = :idReserva"
      + " and ("
      + " (reserva.fechaReserva is not null)"
        + " or reserva.fechaReserva = :fechaReserva)")
  public List<ReservaUsuario> buscarReservaPorFechaAndIdUsuario(@Param("fechaReserva") LocalDate fechaReserva, @Param("idReserva") long idReserva);
 
  public ReservaUsuario findByUsuario_IdUsuarioAndReservaActividad_IdReservaActividad(@Param("idUsuario") long idUsuario, @Param("idReserva") long idReserva);
  
}

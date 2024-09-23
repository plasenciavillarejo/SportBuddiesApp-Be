package es.sport.buddies.entity.app.models.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ReservaUsuario;

public interface IReservaUsuarioDao extends JpaRepository<ReservaUsuario, Long> {

  @Query(value =" select reserva from ReservaUsuario reserva"
      + " left join fetch reserva.usuarioReserva usuReserva"
      + " left join fetch reserva.deporteReserva deportReserva "
      + " where reserva.fechaReserva = :fechaReserva")
  public List<ReservaUsuario> buscarReservaPorFechaAndIdUsuario(@Param("fechaReserva") LocalDate fechaReserva);
  
}

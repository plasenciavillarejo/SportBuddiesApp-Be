package es.sport.buddies.entity.app.models.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.Reserva;

public interface IReservaDao extends JpaRepository<Reserva, Long> {

  @Query(value =" select reserva from Reserva reserva"
      + " left join fetch reserva.usuarioReserva usuReserva"
      + " left join fetch reserva.deporteReserva deportReserva "
      + " where usuReserva.idUsuario = :idUsuReserva"
      + " and reserva.fechaReserva = :fechaReserva")
  public List<Reserva> buscarReservaPorFechaAndIdUsuario(@Param("fechaReserva") LocalDate fechaReserva, @Param("idUsuReserva") long idUsuReserva);
  
}

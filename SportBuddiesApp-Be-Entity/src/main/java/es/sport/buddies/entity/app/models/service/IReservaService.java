package es.sport.buddies.entity.app.models.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.Reserva;

public interface IReservaService {

  public List<Reserva> buscarReservaPorFechaAndIdUsuario(@Param("fechaReserva") LocalDate fechaReserva, @Param("idUsuReserva") long idUsuReserva);
  
}

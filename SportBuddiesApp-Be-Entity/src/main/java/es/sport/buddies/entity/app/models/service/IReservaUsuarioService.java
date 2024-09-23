package es.sport.buddies.entity.app.models.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ReservaUsuario;

public interface IReservaUsuarioService {

  public List<ReservaUsuario> buscarReservaPorFechaAndIdUsuario(@Param("fechaReserva") LocalDate fechaReserva);
  
}

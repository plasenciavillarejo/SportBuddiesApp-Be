package es.sport.buddies.main.app.service;

import java.time.LocalDate;
import java.util.List;

import es.sport.buddies.entity.app.models.entity.Reserva;
import es.sport.buddies.main.app.exceptions.ReservaException;

public interface IReservaMainService {

  public List<Reserva> listarReservas(LocalDate fechaReserva, long idUsuReserva) throws ReservaException;
  
}

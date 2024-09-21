package es.sport.buddies.main.app.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.models.entity.Reserva;
import es.sport.buddies.entity.app.models.service.IReservaService;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaMainService;

@Service
public class ReservaMainServiceImpl implements IReservaMainService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReservaMainServiceImpl.class);
  
  @Autowired
  private IReservaService reservaService;

  @Override
  public List<Reserva> listarReservas(LocalDate fechaReserva, long idUsuReserva) throws ReservaException {
    List<Reserva> res = null;
    try {
      LOGGER.info("Se procede a listar las reservascon fecha: {} para el idUsuario {}", fechaReserva, idUsuReserva);
      res = reservaService.buscarReservaPorFechaAndIdUsuario(fechaReserva, idUsuReserva);
      LOGGER.info(!res.isEmpty() ? "Se ha encontrado un total de {} reservas" : "No se ha encontrado ningúna reserva, se devuelve un listado vacío");
    } catch (Exception e) {
      throw new ReservaException("Error en la busqueda de la reserva");
    }
    return !res.isEmpty() ? res : Collections.emptyList();
  }
  
  
  
}

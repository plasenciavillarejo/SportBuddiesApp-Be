package es.sport.buddies.entity.app.models.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ReservaUsuario;

public interface IReservaUsuarioService {

  public List<ReservaUsuario> buscarReservaPorFechaAndIdUsuario(@Param("fechaReserva") LocalDate fechaReserva, @Param("idReserva") long idReserva);
  
  public void guardarReservaUsuario(ReservaUsuario reservaUsuario);

  public ReservaUsuario findByUsuario_IdUsuarioAndReservaActividad_IdReservaActividad(@Param("idUsuario") long idUsuario, @Param("idReserva") long idReserva);
  
}

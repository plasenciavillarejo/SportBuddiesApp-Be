package es.sport.buddies.entity.app.models.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ReservaUsuario;

public interface IReservaUsuarioService {

  public List<ReservaUsuario> buscarReservaPorFechaAndIdUsuario(@Param("fechaReserva") LocalDate fechaReserva, @Param("idUsuario") long idUsuario,
      @Param("histoiral") boolean historial);
  
  public void guardarReservaUsuario(ReservaUsuario reservaUsuario);

  public ReservaUsuario findByUsuario_IdUsuarioAndReservaActividad_IdReservaActividad(@Param("idUsuario") long idUsuario, @Param("idReserva") long idReserva);
  
  public List<Long> obtenerReservasPorUsuario(@Param("idUsuario") long idUsuario);
 
  public void borrarReservaActividad(@Param("idUsuario") long idUsuario, @Param("idReserva") long idReserva);
  
  public void actualizarAbonoReserva(@Param("idReserva") long idReserva, @Param("metodoPago") String metodoPago);
 
  public ReservaUsuario validarAbonoReserva(@Param("idUsuario") long idUsuario, @Param("idReserva") long idReserva);
  
  public ReservaUsuario findById(@Param("idReservaUsuario") long idReservaUsuario);
  
  public double findByIdReserva(@Param("idReservaUsuario") long idReservaUsuario);
 
  public Page<Object[]> listaConfirmacion(@Param("idUsuario") long idUsuario , Pageable pageable);
  
}

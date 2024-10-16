package es.sport.buddies.main.app.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.dto.ReservaUsuarioDto;
import es.sport.buddies.entity.app.dto.SuscripcionDto;
import es.sport.buddies.entity.app.models.entity.UsuarioPlanPago;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;
import es.sport.buddies.entity.app.models.service.IUsuarioPlanPagoService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.convert.map.struct.IReservaUsuarioMapStruct;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaUsuarioMainService;
import es.sport.buddies.main.app.utils.Utilidades;

@Service
public class ReservaUsuarioMainServiceImpl implements IReservaUsuarioMainService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaUsuarioService reservaUsuarioService;
  
  @Autowired
  private IUsuarioPlanPagoService usuarioPlanPagoService;
  
  @Autowired
  private Utilidades utilidades;
  
  @Override
  public List<ReservaUsuarioDto> listarReservas(LocalDate fechaReserva, long idUsuario, boolean historial) throws ReservaException {
    List<ReservaUsuarioDto> res = null;
    try {
      LOGGER.info("Se procede a listar las reservas con fecha: {}", fechaReserva);
      res = reservaUsuarioService.buscarReservaPorFechaAndIdUsuario(fechaReserva,idUsuario, historial).stream()
          .map(resUsu -> IReservaUsuarioMapStruct.mapper.reservaUsuarioToDto(resUsu)).toList();
      LOGGER.info(!res.isEmpty() ? "Se ha encontrado un total de {} reservas" : "No se ha encontrado ningúna reserva, se devuelve un listado vacío", res.size());
    } catch (Exception e) {
      throw new ReservaException(e.getMessage());
    }
    return !res.isEmpty() ? res : Collections.emptyList();
  }
  
  @Override
  public void eliminarActividad(long idReserva, long idUsuario) throws ReservaException {
    try {
      SuscripcionDto suscripcionDto = utilidades.validarSuscripcion(idUsuario);
      UsuarioPlanPago usuPlanPago = utilidades.obtenerUsuarioPlanPago(suscripcionDto.getIdSuscripcion());
      AtomicLong reserva = new AtomicLong(usuPlanPago.getReservasRestantes());
      usuPlanPago.setReservasRestantes(reserva.incrementAndGet());
      
      LOGGER.info("Se procede a realizar la actulización y la insercción en la BBDD");
      usuarioPlanPagoService.actualizarReservasRestantes(usuPlanPago.getReservasRestantes(),
          usuPlanPago.getIdUsuarioPlanPago());
      LOGGER.info("Reserva restante actualizada correctamente");
      
      LOGGER.info("Se procede a borrar la reserva con ID: {}", idReserva);
      reservaUsuarioService.borrarReservaActividad(idUsuario,idReserva);
      LOGGER.info("Reserva borrada exitosamente");
    } catch (Exception e) {
      throw new ReservaException(e);
    }
  }
  
  
}

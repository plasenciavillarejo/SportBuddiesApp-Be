package es.sport.buddies.main.app.service.impl;

import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import es.sport.buddies.entity.app.dto.ReservaUsuarioDto;
import es.sport.buddies.entity.app.dto.SuscripcionDto;
import es.sport.buddies.entity.app.models.entity.PagoTarjeta;
import es.sport.buddies.entity.app.models.entity.ReservaUsuario;
import es.sport.buddies.entity.app.models.entity.UsuarioPlanPago;
import es.sport.buddies.entity.app.models.service.IPagoTarjetaService;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;
import es.sport.buddies.entity.app.models.service.IUsuarioPlanPagoService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.convert.map.struct.IReservaUsuarioMapStruct;
import es.sport.buddies.main.app.exceptions.CancelarReservaException;
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
    
  @Autowired
  @Qualifier("externalWebClient")
  private WebClient.Builder webClient;
   
  @Autowired
  private IPagoTarjetaService pagoTarjetaService;
  
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
  public void eliminarActividad(long idReservaUsuario, long idUsuario) throws CancelarReservaException {
    try {
      LOGGER.info("Validando que el usuario no haya pagado la reserva");
      ReservaUsuario resUsuario = reservaUsuarioService.validarAbonoReserva(idUsuario, idReservaUsuario);
      if (resUsuario != null && resUsuario.isAbonado()) {
        LocalDate localDateReserva = null;
        /* En la entidad ReservaUsuario el campo fechaReserva al estar anotado con @Temporal(TemporalType.DATE) hibernate lo transforma a un java.sql.date
          por esto tenemos que realizar el casteo de la siguiente manera */
        if (resUsuario.getFechaReserva() instanceof Date date) {
          localDateReserva = (date).toLocalDate();
        } else {
          localDateReserva = LocalDate.ofInstant(resUsuario.getFechaReserva().toInstant(), ZoneId.systemDefault()); 
        }
        if(localDateReserva.isEqual(LocalDate.now()) || localDateReserva.isEqual(LocalDate.now().plusDays(1))) {
          throw new CancelarReservaException("Lo sentimos, no se puede cancelar la reserva el mismo día o el día antes de la actividad.");
        }
      }
      
      SuscripcionDto suscripcionDto = utilidades.validarSuscripcion(idUsuario);
      UsuarioPlanPago usuPlanPago = utilidades.obtenerUsuarioPlanPago(suscripcionDto.getIdSuscripcion());
      AtomicLong reserva = new AtomicLong(usuPlanPago.getReservasRestantes());
      usuPlanPago.setReservasRestantes(reserva.incrementAndGet());
      
      LOGGER.info("Se procede a realizar la actulización y la insercción en la BBDD");
      usuarioPlanPagoService.actualizarReservasRestantes(usuPlanPago.getReservasRestantes(),
          usuPlanPago.getIdUsuarioPlanPago());
      LOGGER.info("Reserva restante actualizada correctamente");
      
      if(resUsuario.isAbonado() && resUsuario.getMetodoPago().equalsIgnoreCase(ConstantesMain.METODOPAGOPAYPAL)) {
        LOGGER.info("Se procede a devolver el pago");
        devolverPagoPaypal(idReservaUsuario);
        LOGGER.info("Pago devuelto exitosamente");
      } else if(resUsuario.isAbonado() && resUsuario.getMetodoPago().equalsIgnoreCase(ConstantesMain.METODOPAGOTARJETA)){
        PagoTarjeta tarjeta = pagoTarjetaService.findByReservaUsuario_IdReserva(resUsuario.getIdReserva()); 
        if(tarjeta != null) {
          LOGGER.info("Se procede a realizar el pago con tarjeta");
          devolverPagoTarjeta(tarjeta.getIdDevolucion());
        }
      }
      
      LOGGER.info("Se procede a borrar la reserva con ID: {}", idReservaUsuario);
      reservaUsuarioService.borrarReservaActividad(idUsuario,idReservaUsuario);
      LOGGER.info("Reserva borrada exitosamente");
      
      LOGGER.info("Se procede a aumentar la vacantes restantes en 1 en la Reserva Actividad");
      // Llamar Utils plasencia
      utilidades.actualizarReservasRestantes(false, resUsuario.getReservaActividad().getIdReservaActividad());
      
    } catch (Exception e) {
      throw new CancelarReservaException(e);
    }
  }
  
  /**
   * Función encargada de comunicarse con el endpoint de paypal para devolver el pago
   * @param idReservaUsuario
   */
  private void devolverPagoPaypal(long idReservaUsuario) {
    URI uri = UriComponentsBuilder.fromUriString(ConstantesMain.SPORTBUDDIESGTW.concat("/api/main")).path("/paypal/devolver/pago")
        .queryParam("idReservaUsuario", idReservaUsuario)
        .build().toUri();
    webClient.build().post().uri(uri)
    .headers(headers -> headers.setBearerAuth(ConstantesMain.TOKEN)).retrieve()
    .bodyToMono(Void.class)
    .doOnSubscribe(
        subscription -> LOGGER.info("Sending request to URI with idReservaUsuario: {}", idReservaUsuario))
    .doOnError(error -> LOGGER.error("Error: {}", error.getMessage(), error.getCause()))
    .block();
  }

  /**
   * Función encargada de comunicarse con el endpoint de pago-tarjeta para devolver el pago
   * @param paymentIntentId
   */
  private void devolverPagoTarjeta(String paymentIntentId) {
    URI uri = UriComponentsBuilder.fromUriString(ConstantesMain.SPORTBUDDIESGTW.concat("/api/main")).path("/tarjeta/devolver/pago")
        .queryParam("paymentIntentId", paymentIntentId)
        .build().toUri();
    webClient.build().post().uri(uri)
    .headers(headers -> headers.setBearerAuth(ConstantesMain.TOKEN)).retrieve()
    .bodyToMono(Void.class)
    .doOnSubscribe(
        subscription -> LOGGER.info("Sending request to URI with paymentIntentId: {}", paymentIntentId))
    .doOnError(error -> LOGGER.error("Error: {}", error.getMessage(), error.getCause()))
    .block();
  }
  
  @Override
  public double obtenerPrecioActividad(long idReservaUsuario) throws CancelarReservaException {
    return reservaUsuarioService.findByIdReserva(idReservaUsuario);
  }
   
}

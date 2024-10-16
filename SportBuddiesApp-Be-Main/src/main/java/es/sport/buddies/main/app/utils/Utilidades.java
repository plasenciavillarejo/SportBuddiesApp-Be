package es.sport.buddies.main.app.utils;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.sport.buddies.entity.app.dto.SuscripcionDto;
import es.sport.buddies.entity.app.models.entity.UsuarioPlanPago;
import es.sport.buddies.entity.app.models.service.ISuscripcionService;
import es.sport.buddies.entity.app.models.service.IUsuarioPlanPagoService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.convert.map.struct.ISuscripcionMapStruct;
import es.sport.buddies.main.app.exceptions.ReservaException;

@Component
public class Utilidades {

  private static final Logger LOGGER = LoggerFactory.getLogger(Utilidades.class);
  
  @Autowired
  private ISuscripcionService suscripcionService;
  
  @Autowired
  private IUsuarioPlanPagoService usuarioPlanPagoService;

  
  /**
   * Función encargada de validar una sucripción activa
   * @param inscripcionReservaActividad
   * @return
   * @throws ReservaException
   */
  public SuscripcionDto validarSuscripcion(long inscripcionReservaActividad) throws ReservaException {
    LOGGER.info("Se procede a validar si el usuario tiene una suscripción activa");
    SuscripcionDto suscripcionDto = ISuscripcionMapStruct.mapper.sucricpcionToDto(suscripcionService.findByUsuario_IdUsuario(inscripcionReservaActividad));
    if(suscripcionDto == null || !suscripcionDto.getEstadoPago().equalsIgnoreCase(ConstantesMain.ACTIVO)) {
      throw new ReservaException("El usuario actualmente no tiene una suscripcion activa");
    }
    LOGGER.info("Suscripción Activa - OKEY");
    return suscripcionDto;
  }
  
  /**
   * Función para buscar si el usuario contiene una suscripcion activa
   * @param idSuscripcion
   * @return
   */
  public UsuarioPlanPago obtenerUsuarioPlanPago(long idSuscripcion) {
    return usuarioPlanPagoService.findBySuscripcion_IdSuscripcion(idSuscripcion);
  }
  
}

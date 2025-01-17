package es.sport.buddies.main.app.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import es.sport.buddies.entity.app.dto.PaginadorDto;
import es.sport.buddies.entity.app.dto.SuscripcionDto;
import es.sport.buddies.entity.app.models.entity.ReservaActividad;
import es.sport.buddies.entity.app.models.entity.UsuarioPlanPago;
import es.sport.buddies.entity.app.models.service.IReservaActividadService;
import es.sport.buddies.entity.app.models.service.ISuscripcionService;
import es.sport.buddies.entity.app.models.service.IUsuarioPlanPagoService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.convert.map.struct.ISuscripcionMapStruct;
import es.sport.buddies.main.app.exceptions.CrearReservaException;
import es.sport.buddies.main.app.exceptions.ReservaException;

@Component
public class Utilidades {

  private static final Logger LOGGER = LoggerFactory.getLogger(Utilidades.class);
  
  @Autowired
  private ISuscripcionService suscripcionService;
  
  @Autowired
  private IUsuarioPlanPagoService usuarioPlanPagoService;

  @Autowired
  private IReservaActividadService reservaActividadService;
  
  /**
   * Función encargada de mapear todas las rutas públicas dentro de la aplicacion
   */
  public List<String> publicRoutes = Arrays.asList("/reservaActividad/listarReserva",
      "/reservaActividad/comboInicio",
      "/reservaActividad/listadoMunicipios",
      "/reservaActividad/listadoReserva",
      "/borrarCookie",
      "/swagger-ui/**",
      "/api-docs/**",
      "/estado/pago",
      "/usuario/crear");

  
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
  
  /**
   * Función encargada de restar o sumar una plaza restante dentro de las ReservaActividad
   * @param restarPlaza
   * @param idReservaActividad
   * @return
   * @throws CrearReservaException
   * @throws ReservaException 
   */
  public void actualizarReservasRestantes(boolean restarPlaza, long idReservaActividad) throws CrearReservaException, ReservaException {
    LOGGER.info("Recuperando la reserva actividad por su ID: {}", idReservaActividad);
    ReservaActividad reservaActividad = reservaActividadService.findById(idReservaActividad);
    
    if(reservaActividad == null) {
      throw new CrearReservaException("No existe la reserva con ID: " + idReservaActividad);
    } 
    if(restarPlaza && reservaActividad.getPlazasRestantes() - 1 >= 0) {
      LOGGER.info("Se procede a restar una plaza");
      reservaActividad.setPlazasRestantes(reservaActividad.getPlazasRestantes() - 1);
    } else {
      LOGGER.info("Se procede a sumar una plaza");
      reservaActividad.setPlazasRestantes(reservaActividad.getPlazasRestantes() + 1);
    }
    
    LOGGER.info("Se procede actualizar las reservas de las actividades restantes");
    actualizarReservaRestante(reservaActividad);
    LOGGER.info("Reservas actualizadas exitosamente");
  }
  
  /**
   * Función encargada de restar una plaza al total existente
   * @throws ReservaException
   */
  private void actualizarReservaRestante(ReservaActividad reservaActividad) throws ReservaException {
    try {
      reservaActividadService.actualizarPlazaRestantes(reservaActividad.getIdReservaActividad(), reservaActividad.getPlazasRestantes());
    } catch (Exception e) {
      throw new ReservaException(e);
    }
  }
  
  /**
   * Configuramos el paginador
   * @param paginador
   * @param page
   */
  public void configurarPaginador(PaginadorDto paginador, Pageable page) {
    paginador.setPaginaActual(page.getPageNumber() + 1);
    paginador.setTamanioPagina(page.getPageSize());
  }
  
  /**
   * Función para el paginador de forma global
   * @param pagina
   * @param tamanioPagina
   * @param orden
   * @param campo
   * @return
   * @throws ReservaException
   */
  public Pageable configurarPageRequest(int pagina, int tamanioPagina, int orden, String campo) throws ReservaException {
    Pageable pag = null;
    try {
      pag = PageRequest.of(pagina - 1, tamanioPagina,
          orden == 1 ? Sort.by(campo).ascending() : Sort.by(campo).descending());
    } catch (Exception e) {
      throw new ReservaException(e.getMessage(), e.getCause());
    }
    return pag;
  }

  /**
   * Función para crear el paginador
   * @param <T>
   * @param paginador
   * @param pageable
   * @param listPage
   */
  public <T> PaginadorDto configPaginator(Pageable pageable,Page<T> listPage) {
    PaginadorDto paginador = new PaginadorDto();
    LOGGER.info("Configurando el paginador");
    configurarPaginador(paginador, pageable);
    paginador.setRegistros((int)listPage.getTotalElements());
    return paginador;
  }
  
  public static String generateSecureRandomPassword() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] randomBytes = new byte[16];
    secureRandom.nextBytes(randomBytes);

    // Convertir los bytes aleatorios a una cadena legible
    return Base64.getUrlEncoder().encodeToString(randomBytes);
  }
  
}

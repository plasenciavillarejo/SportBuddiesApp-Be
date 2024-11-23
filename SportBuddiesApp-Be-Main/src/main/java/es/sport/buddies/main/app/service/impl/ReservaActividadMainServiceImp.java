package es.sport.buddies.main.app.service.impl;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.dto.CrearReservaActividadDto;
import es.sport.buddies.entity.app.dto.InscripcionReservaActividadDto;
import es.sport.buddies.entity.app.dto.ListadoReservaActividadDto;
import es.sport.buddies.entity.app.dto.PaginadorDto;
import es.sport.buddies.entity.app.dto.ReservaActividadDto;
import es.sport.buddies.entity.app.dto.SuscripcionDto;
import es.sport.buddies.entity.app.models.entity.ReservaActividad;
import es.sport.buddies.entity.app.models.entity.ReservaUsuario;
import es.sport.buddies.entity.app.models.entity.UsuarioPlanPago;
import es.sport.buddies.entity.app.models.service.IDeporteService;
import es.sport.buddies.entity.app.models.service.IMunicipioService;
import es.sport.buddies.entity.app.models.service.IProvinciaService;
import es.sport.buddies.entity.app.models.service.IReservaActividadService;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;
import es.sport.buddies.entity.app.models.service.IUsuarioPlanPagoService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.constantes.ConstantesMessages;
import es.sport.buddies.main.app.convert.map.struct.IReservaActividadMapStruct;
import es.sport.buddies.main.app.convert.map.struct.IReservaUsuarioMapStruct;
import es.sport.buddies.main.app.exceptions.CrearReservaException;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaActividadMainService;
import es.sport.buddies.main.app.utils.Utilidades;

@Service
public class ReservaActividadMainServiceImp implements IReservaActividadMainService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaActividadService reservaActividadService;
  
  @Autowired
  private IDeporteService deporteService;
  
  @Autowired
  private IProvinciaService provinciaService;
  
  @Autowired
  private IMunicipioService muncipioService;
    
  @Autowired
  private IUsuarioPlanPagoService usuarioPlanPagoService;
  
  @Autowired
  private MessageSource messageSource;
  
  @Autowired
  private IReservaUsuarioService reservaUsuarioService;
    
  @Autowired
  private Utilidades utilidades;
  
  @Override
  public Map<String, Object> listadoReservaActividad(ListadoReservaActividadDto listadoDto, Pageable pageable) throws ReservaException {
    Map<String, Object> params = new HashMap<>();
    Page<ReservaActividad> listPage = null;
    try {
      if(!listadoDto.getFechaReserva().isBefore(LocalDate.now())) {
        listPage = reservaActividadService.findByFechaReservaAndActividadAndProvinciaAndMunicipio(
            Date.from(listadoDto.getFechaReserva().atStartOfDay( ZoneOffset.UTC ).toInstant()),
             listadoDto.getActividad(), listadoDto.getProvincia(), listadoDto.getMunicipio(), pageable);
                
        LOGGER.info("Se procede agregar la información en el MAP");
        params.put("listActividad", listPage.getContent().stream().map(res -> IReservaActividadMapStruct.mapper.reservarActividadToDto(res)).toList());
        params.put("paginador", utilidades.configPaginator(pageable, listPage));
      } else {
        LOGGER.info("No se ha encontrando ningún registro con dichas características");
        params.put("listActividad", Collections.emptyList());
      }      
    } catch (Exception e) {
      throw new ReservaException(e.getMessage());
    }
    return params;
  }

  @Override
  public void crearReservaActivdad(CrearReservaActividadDto reservaActividadDto) throws CrearReservaException {
    try {
      LOGGER.info("Se procede a validar que el usuario no contiene una reserva para la misma fecha, hora inico y hora fin: {} {}-{}"
          + "", reservaActividadDto.getFechaReserva(), reservaActividadDto.getHoraInicio(), reservaActividadDto.getHoraFin());
      ReservaActividadDto resDto =  IReservaActividadMapStruct.mapper.reservarActividadToDto(reservaActividadService.
          findByProvinciaAndMunicipioAndFechaReservaAndHoraInicioAndHoraFinAndUsuarioActividad_IdUsuario(reservaActividadDto.getProvincia(),
              reservaActividadDto.getMunicipio(), reservaActividadDto.getFechaReserva(), reservaActividadDto.getHoraInicio(),
              reservaActividadDto.getHoraFin(), reservaActividadDto.getIdUsuarioActividadDto()));
      
      if(resDto != null) {
        throw new CrearReservaException("El usuario ya contiene una reserva para el mismo día");
      } 
      
      reservaActividadService.guardarReservaActividad(IReservaActividadMapStruct.mapper.crearReservaActividadToEntity(reservaActividadDto));
      
      LOGGER.info("Se ha creado la reserva correctamente para el usuario {}",reservaActividadDto.getIdUsuarioActividadDto());
    } catch (Exception e) {
      throw new CrearReservaException(e.getMessage());
    }
  }

  @Override
  public Map<String, Object> listarCombosPaginaInicial(boolean provincias) throws ReservaException {
    return provincias ? Map.ofEntries(Map.entry("listaProvincias", provinciaService.listarProvincias()))
        : Map.ofEntries(
            Map.entry("listadoDeportes", deporteService.listarDeportes()),
            Map.entry("listaProvincias", provinciaService.listarProvincias()));
  }

  @Override
  public List<String> listaMunicipiosPorProvinca(String nombreProvincia) {
    return muncipioService.findByProvincia_NombreProvincia(nombreProvincia)
        .stream().map(provin -> provin.getNombreMunicipio()).toList();
  }
  
  @Override
  @Transactional // Indico el Rollback de forma directa en el servicio para que se haga el rollback del update en caso de que falle el insert
  public void inscripcionReservaActividad(InscripcionReservaActividadDto inscripcionReservaActividad) throws ReservaException, CrearReservaException {
    try {
      SuscripcionDto suscripcionDto = utilidades.validarSuscripcion(inscripcionReservaActividad.getIdUsuario());
      
      LOGGER.info("Validando que el usuario no este inscrito en la reserva con ID: {}", inscripcionReservaActividad.getIdReservaActividad());
      ReservaUsuario reservaUsuario = reservaUsuarioService.findByUsuario_IdUsuarioAndReservaActividad_IdReservaActividad(inscripcionReservaActividad.getIdUsuario(),
          inscripcionReservaActividad.getIdReservaActividad());
      
      if(reservaUsuario != null) {
        throw new CrearReservaException("Ya estás inscrito en la actividad");
      }

      LOGGER.info("Validando el tipo de plan que contiene el usuario y sus reservas restantes");
      UsuarioPlanPago usuPlanPago = utilidades.obtenerUsuarioPlanPago(suscripcionDto.getIdSuscripcion());
      AtomicLong reserva = new AtomicLong(usuPlanPago.getReservasRestantes());
      
      if(usuPlanPago.getReservasRestantes() > 0 || usuPlanPago.getPlanPago().getNombrePlan().equalsIgnoreCase(ConstantesMain.FREE)) {
        LOGGER.info("El usuario: '{}' contiene '{}' reservas restantes, se prodece a realizar la inscripción a la reserva con ID: {}",
            usuPlanPago.getSuscripcion().getUsuario().getNombreUsuario(), usuPlanPago.getReservasRestantes(),
            inscripcionReservaActividad.getIdReservaActividad());
        usuPlanPago.setReservasRestantes(reserva.decrementAndGet());
        LOGGER.info("Se procede a realizar la actualización y la insercción en la BBDD");
        guardarReservaUsuario(usuPlanPago,inscripcionReservaActividad);
        LOGGER.info("Se ha realizado la reserva existosamente");
        
        // Recuperamos la ReservaActividad para restar una reserva a las plazasRestantes.
        utilidades.actualizarReservasRestantes(true, inscripcionReservaActividad.getIdReservaActividad());
        
      } else {
        throw new ReservaException(messageSource.getMessage(ConstantesMessages.RESERVASAGOTADAS, null, LocaleContextHolder.getLocale()));
      }
    } catch (CrearReservaException e) {
      throw e;
    } catch (ReservaException e2) {
      throw e2;
    } 
  }
  
  /**
   * Función encargada de guardar la Reserva Usuario
   * @param usuPlanPago
   * @param inscripcionReservaActividad
   * @throws ReservaException
   */
  private void guardarReservaUsuario(UsuarioPlanPago usuPlanPago,
      InscripcionReservaActividadDto inscripcionReservaActividad) throws ReservaException {
    try {
      usuarioPlanPagoService.actualizarReservasRestantes(usuPlanPago.getReservasRestantes(),
          usuPlanPago.getIdUsuarioPlanPago());
      LOGGER.info("Se ha actualizado correctamente");
      LOGGER.info("Se procede a insertar la reserva para el usuario");
      reservaUsuarioService.guardarReservaUsuario(
          IReservaUsuarioMapStruct.mapper.inscripcionActividadToReservaActividad(inscripcionReservaActividad));
    } catch (Exception e) {
      throw new ReservaException(e);
    }
  }

  @Override
  public List<Long> listarActividadInscritas(long idUsuario) {
    return reservaUsuarioService.obtenerReservasPorUsuario(idUsuario);
  }

  @Override
  public Map<String, Object> listarReservaActividaPorId(long idUsuario,Pageable pageable) throws ReservaException {
    Map<String, Object> params = new HashMap<String, Object>();
    Page<ReservaActividad> listPage = null;
    
    listPage = reservaActividadService.findByUsuarioActividad_IdUsuario(idUsuario, pageable);
    params.put("listActividad", listPage.getContent().stream().map(res -> IReservaActividadMapStruct.mapper.reservarActividadToDto(res)).toList());  
    params.put("paginador", utilidades.configPaginator(pageable, listPage));
    return params;
  }
  
}
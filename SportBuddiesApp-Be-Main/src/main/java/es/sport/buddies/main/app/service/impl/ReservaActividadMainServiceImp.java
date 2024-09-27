package es.sport.buddies.main.app.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.dto.CrearReservaActividadDto;
import es.sport.buddies.entity.app.dto.ListadoReservaActividadDto;
import es.sport.buddies.entity.app.dto.ReservaActividadDto;
import es.sport.buddies.entity.app.models.service.IReservaActividadService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.convert.map.struct.IReservaActividadMapStruct;
import es.sport.buddies.main.app.exceptions.CrearReservaException;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaActividadMainService;

@Service
public class ReservaActividadMainServiceImp implements IReservaActividadMainService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaActividadService reservaActividadService;
  
  @Override
  public List<ReservaActividadDto> listadoReservaActividad(ListadoReservaActividadDto listadoDto) throws ReservaException, InterruptedException, ExecutionException {
    //CompletableFuture<List<ReservaActividadDto>> list = new CompletableFuture<>();
    List<ReservaActividadDto> listDos = null;
    try {
      /*
      list = CompletableFuture.supplyAsync(() -> 
      reservaActividadService.findByFechaReservaAndActividadAndProvinciaAndMunicipio(listadoDto.getFechaReserva(),
          listadoDto.getActividad(), listadoDto.getProvincia(), listadoDto.getMunicipio())
      .stream()
      .peek(res -> LOGGER.info(res != null ? "Recibiendo una reserva actividad con ID: {} " : "No se han recibido ningúna reserva actividad", res.getIdReservaActividad()))
      .map(res -> IReservaActividadMapStruct.mapper.reservarActividadToDto(res))
      .toList()).thenApply(lista -> lista);
      CompletableFuture.allOf(list).get();
      */
      listDos = reservaActividadService.findByFechaReservaAndActividadAndProvinciaAndMunicipio(listadoDto.getFechaReserva(), listadoDto.getActividad(),
          listadoDto.getProvincia(), listadoDto.getMunicipio()).stream()
          .peek(res -> LOGGER.info(res != null ? "Recibiendo una reserva actividad con ID: {} " : "No se han recibido ningúna reserva actividad", res.getIdReservaActividad()))
          .map(res -> IReservaActividadMapStruct.mapper.reservarActividadToDto(res)).toList();
    } catch (Exception e) {
      throw new ReservaException(e.getMessage());
    }
    //return !list.get().isEmpty() ? list.get() : Collections.emptyList();
    return listDos;
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

}

package es.sport.buddies.main.app.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.dto.ReservaUsuarioDto;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.convert.map.struct.IReservaUsuarioMapStruct;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaUsuarioMainService;

@Service
public class ReservaUsuarioMainServiceImpl implements IReservaUsuarioMainService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaUsuarioService reservaService;
    
  @Override
  public List<ReservaUsuarioDto> listarReservas(LocalDate fechaReserva, long idUsuario) throws ReservaException {
    List<ReservaUsuarioDto> res = null;
    try {
      LOGGER.info("Se procede a listar las reservas con fecha: {}", fechaReserva);
      res = reservaService.buscarReservaPorFechaAndIdUsuario(fechaReserva,idUsuario).stream()
          .map(resUsu -> IReservaUsuarioMapStruct.mapper.reservaUsuarioToDto(resUsu)).toList();
      LOGGER.info(!res.isEmpty() ? "Se ha encontrado un total de {} reservas" : "No se ha encontrado ningúna reserva, se devuelve un listado vacío", res.size());
    } catch (Exception e) {
      throw new ReservaException(e.getMessage());
    }
    return !res.isEmpty() ? res : Collections.emptyList();
  }
  
}

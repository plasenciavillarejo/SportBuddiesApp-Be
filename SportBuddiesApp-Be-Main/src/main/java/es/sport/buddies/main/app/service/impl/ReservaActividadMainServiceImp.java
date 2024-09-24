package es.sport.buddies.main.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.dto.ListadoReservaActividadDto;
import es.sport.buddies.entity.app.dto.ReservaActividadDto;
import es.sport.buddies.entity.app.models.service.IReservaActividadService;
import es.sport.buddies.main.app.convert.map.struct.IReservaActividadMapStruct;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaActividadMainService;

@Service
public class ReservaActividadMainServiceImp implements IReservaActividadMainService {

  @Autowired
  private IReservaActividadService reservaActividadService;
  
  @Override
  public List<ReservaActividadDto> listadoReservaActividad(ListadoReservaActividadDto listadoDto) throws ReservaException {
    List<ReservaActividadDto> list = null;
    try {
      list = reservaActividadService.findByFechaReservaAndActividadAndProvinciaAndMunicipio(listadoDto.getFechaReserva(), listadoDto.getActividad(),
          listadoDto.getProvincia(), listadoDto.getMunicipio()).stream().map(res -> IReservaActividadMapStruct.mapper.reservarActividadToDto(res)).toList();
    } catch (Exception e) {
      throw new ReservaException(e.getMessage());
    }
    return list;
  }

}

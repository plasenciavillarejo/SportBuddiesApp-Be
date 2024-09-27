package es.sport.buddies.main.app.convert.map.struct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import es.sport.buddies.entity.app.dto.CrearReservaActividadDto;
import es.sport.buddies.entity.app.dto.ReservaActividadDto;
import es.sport.buddies.entity.app.models.entity.ReservaActividad;

@Mapper
public interface IReservaActividadMapStruct {

  public IReservaActividadMapStruct mapper = Mappers.getMapper(IReservaActividadMapStruct.class);
  
  @Mapping(target = "usuarioActividadDto", source = "usuarioActividad")
  public ReservaActividadDto reservarActividadToDto(ReservaActividad reservaActividad);
  
  @Mapping(target = "usuarioActividad.idUsuario", source = "idUsuarioActividadDto")
  public ReservaActividad crearReservaActividadToEntity(CrearReservaActividadDto reservaActividadDto);
  
}

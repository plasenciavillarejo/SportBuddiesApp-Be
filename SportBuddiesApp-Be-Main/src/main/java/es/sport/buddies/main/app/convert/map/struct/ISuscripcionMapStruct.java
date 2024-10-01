package es.sport.buddies.main.app.convert.map.struct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import es.sport.buddies.entity.app.dto.SuscripcionDto;
import es.sport.buddies.entity.app.models.entity.Suscripcion;

@Mapper
public interface ISuscripcionMapStruct {

  public ISuscripcionMapStruct mapper = Mappers.getMapper(ISuscripcionMapStruct.class);
  
  @Mapping(target = "usuarioDto", source = "usuario")
  public SuscripcionDto sucricpcionToDto(Suscripcion suscrpcion);
 
  
}

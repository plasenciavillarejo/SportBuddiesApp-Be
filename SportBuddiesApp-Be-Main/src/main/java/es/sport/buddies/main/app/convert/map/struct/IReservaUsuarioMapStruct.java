package es.sport.buddies.main.app.convert.map.struct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import es.sport.buddies.entity.app.dto.ReservaUsuarioDto;
import es.sport.buddies.entity.app.models.entity.ReservaUsuario;

@Mapper
public interface IReservaUsuarioMapStruct {

  // Instanciamos la implementación de la interfaz mediante el Mapper de mapstruct, cuando compile creara una implementación
  public IReservaUsuarioMapStruct mapper = Mappers.getMapper(IReservaUsuarioMapStruct.class);
  
  /* Método para mapear un ReservaUsuario a un ReservaUsuarioDto
      1.- Si un campo se llama diferente, se debe de especificar. De lo contrario su valor saldra null
         - Indicamos el campo asociado al Dto y al que hace referencia en la entidad
   * */
  @Mapping(target = "usuarioReservaDto", source = "usuarioReserva")
  @Mapping(target = "deporteReservaDto", source = "deporteReserva")
  public ReservaUsuarioDto reservaUsuarioDto(ReservaUsuario resUsu); 
  
}

package es.sport.buddies.main.app.convert.map.struct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import es.sport.buddies.entity.app.dto.InscripcionReservaActividadDto;
import es.sport.buddies.entity.app.dto.ReservaUsuarioDto;
import es.sport.buddies.entity.app.models.entity.ReservaUsuario;

@Mapper
public interface IReservaUsuarioMapStruct {

  // Instanciamos la implementación de la interfaz mediante el Mapper de mapstruct, cuando compile creara una implementación
  public IReservaUsuarioMapStruct mapper = Mappers.getMapper(IReservaUsuarioMapStruct.class);
  
  /* Método para mapear un ReservaUsuario a un ReservaUsuarioDto
      1.- Si un campo se llama diferente, se debe de especificar. De lo contrario su valor saldra null
         - Indicamos el campo asociado al Dto y al que hace referencia en la entidad
      2.- Si el campo diferente está dentro de una tabla referenciada dentro de la entidad debermos de realizar la siguiente acción:
           @Mapping(target = "usuarioReservaDto.idUsuarioDto", source = "usuarioReserva.idUsuario")
   * */
  @Mapping(target = "usuarioReservaDto", source = "usuario")
  @Mapping(target = "deporteReservaDto", source = "deporte")
  @Mapping(target = "reservaActividadDto", source = "reservaActividad")
  public ReservaUsuarioDto reservaUsuarioToDto(ReservaUsuario resUsu); 

  @Mapping(target = "usuario.idUsuario", source = "idUsuario")
  @Mapping(target = "deporte.idDeporte", source = "idDeporte")
  @Mapping(target = "reservaActividad.idReservaActividad", source = "idReservaActividad")
  public ReservaUsuario inscripcionActividadToReservaActividad(InscripcionReservaActividadDto inscripcionDto);
  
  
}

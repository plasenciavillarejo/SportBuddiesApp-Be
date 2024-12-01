package es.sport.buddies.main.app.convert.map.struct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import es.sport.buddies.entity.app.dto.ConfirmarUsuarioDto;
import es.sport.buddies.entity.app.dto.UsuariosConfirmadosDto;
import es.sport.buddies.entity.app.models.entity.ConfirmacionUsuario;

@Mapper
public interface IConfirmacionUsuarioMapStruct {

  public IConfirmacionUsuarioMapStruct mapper = Mappers.getMapper(IConfirmacionUsuarioMapStruct.class);
 
  @Mapping(target = "usuario.idUsuario", source = "idUsuario")
  @Mapping(target = "reservaActividad.idReservaActividad", source = "idReservaActividad")
  public ConfirmacionUsuario confimacionUsuarioDtoToEntity(ConfirmarUsuarioDto confirmacionUsuarioDto);
   
  @Mapping(target = "idUsuario", source = "usuario.idUsuario")
  public UsuariosConfirmadosDto confirmacionUsuariosToDto(ConfirmacionUsuario confirmacionUsuario);
  
}

package es.sport.buddies.main.app.convert.map.struct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.sport.buddies.entity.app.dto.UsuarioDto;
import es.sport.buddies.entity.app.models.entity.Usuario;

@Mapper
public interface IUsuarioMapStruct {

  public IUsuarioMapStruct mapper = Mappers.getMapper(IUsuarioMapStruct.class);
  
  public Usuario crearUsuarioEntity(UsuarioDto usuarioDto);
  
}

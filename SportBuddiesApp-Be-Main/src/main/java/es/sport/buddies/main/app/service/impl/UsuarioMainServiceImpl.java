package es.sport.buddies.main.app.service.impl;

import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.dto.UsuarioDto;
import es.sport.buddies.entity.app.models.entity.ReservaUsuario;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;
import es.sport.buddies.entity.app.models.service.IRoleService;
import es.sport.buddies.entity.app.models.service.IUsuarioService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.convert.map.struct.IUsuarioMapStruct;
import es.sport.buddies.main.app.exceptions.UsuarioException;
import es.sport.buddies.main.app.service.IUsuarioMainService;

@Service
public class UsuarioMainServiceImpl implements IUsuarioMainService {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);

  @Autowired
  private BCryptPasswordEncoder bCryptPass;
  
  @Autowired
  private IUsuarioService usuariosService;
  
  @Autowired
  private IRoleService roleServie;
    
  @Autowired
  private IReservaUsuarioService reservaUsuarioService;
  
  @Override
  public void crearNuevoUsuario(UsuarioDto usuarioDto) throws UsuarioException {
    LOGGER.info("Se procede a validar que el nombre del usuario no exista");
    Usuario usuario = usuariosService.findByNombreUsuario(usuarioDto.getNombreUsuario());
    
    if(usuario != null){
      throw new UsuarioException("El nombre de usuario ya existe, por favor, vuelva a probar con otro.");
    }
    
    usuario = usuariosService.findByEmail(usuarioDto.getEmail());
    if(usuario != null) {
      throw new UsuarioException("El correo indicado ya existe, por favor, vuelva a probar con otro.");
    }
    
    usuarioDto.setPassword(bCryptPass.encode(usuarioDto.getPassword()));
    usuario = IUsuarioMapStruct.mapper.crearUsuarioEntity(usuarioDto);
    usuario.setRoles(Arrays.asList(roleServie.findByNombreRol("USER")));
    usuario.setEnabled(true);
    
    // Al guardar al usario, se almacena en la tabla UsuarioInRol su relación entre usuario y el rol
    guardarUsuario(usuario);
    
  }

  /**
   * Función para almacenar al usu
   * @param usuario
   * @throws UsuarioException
   */
  public void guardarUsuario(Usuario usuario) throws UsuarioException {
    try {
      LOGGER.info("Se procede a guardar al usuario");
      usuariosService.guardarUsuario(usuario);
      LOGGER.info("Usuario guardado correctamente");
    } catch (Exception e) {
      throw new UsuarioException(e);
    }
  }

  /**
   * Función encargada de confirmar la rececpción del pago
   */
  @Override
  public Map<String, String> confirmacionPago(long idReservaUsuario, Map<String, String> mapResponse) {
    LOGGER.info("Se procede a obtener los datos de la ReservaUsuario: ");
    ReservaUsuario res = reservaUsuarioService.findById(idReservaUsuario);
    if (res != null) {
      LOGGER.info("Reserva obtenida correctamente, se procede a confirmar el pago");
      reservaUsuarioService.actualizarAbonoReserva(idReservaUsuario);
      mapResponse.put(ConstantesMain.SUCCESS, "Pago realizado correctamente");
    } else {
      mapResponse.put(ConstantesMain.ERRROR, "Pago incorrecto");
    }
    return mapResponse;
  }

}

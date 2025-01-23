package es.sport.buddies.main.app.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.dto.UsuarioDto;
import es.sport.buddies.entity.app.models.entity.PlanPago;
import es.sport.buddies.entity.app.models.entity.Suscripcion;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.entity.UsuarioPlanPago;
import es.sport.buddies.entity.app.models.service.IPlanPagoService;
import es.sport.buddies.entity.app.models.service.IRoleService;
import es.sport.buddies.entity.app.models.service.ISuscripcionService;
import es.sport.buddies.entity.app.models.service.IUsuarioPlanPagoService;
import es.sport.buddies.entity.app.models.service.IUsuarioService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.convert.map.struct.IUsuarioMapStruct;
import es.sport.buddies.main.app.exceptions.UsuarioException;
import es.sport.buddies.main.app.service.IUsuarioMainService;
import es.sport.buddies.main.app.utils.Utilidades;

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
  private ISuscripcionService suscripcionService;
  
  @Autowired
  private IPlanPagoService planPagoService;
  
  @Autowired
  private IUsuarioPlanPagoService usuarioPlanPagoService;
  
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
    
    usuarioDto.setPassword(usuarioDto.getPassword() != null ? bCryptPass.encode(usuarioDto.getPassword()) : 
      bCryptPass.encode(Utilidades.generateSecureRandomPassword()));
    usuario = IUsuarioMapStruct.mapper.crearUsuarioEntity(usuarioDto);
    usuario.setRoles(Arrays.asList(roleServie.findByNombreRol("USER")));
    usuario.setEnabled(true);    
    // Al guardar al usario, se almacena en la tabla UsuarioInRol su relación entre usuario y el rol
    guardarUsuario(usuario); 
    
    /* PLASENCIA - UNA VEZ QUE GUARDO AL USUARIO DEBEO DE CREAR POR DEFECTO UNA SUSCRIPCION PARA DARLE COMO ACTIVA, DEBIDO A QUE AL REALIZAR UNA SUSCRIPCIÓN VALIDA PRIMERO
    SI ESTÁ ESTA ACTIVA LUEGO. SE DEBE DE ASIGNAR A LA TABLA USUARIOS_PLAN_PAGO LA CUAL ESTA CONTIENE AL USUARIO REGISTRADO Y LAS RESERVAS RESTANTES QUE LE QUEDAN,
    ESTAS RESERVAS RESTANTES SE OBTIENE POR DEFECTO DE UNA TABLA MAESTRA LLAMADA PLANES_DE_PAGO */
    Suscripcion suscripcion = Suscripcion.builder()
        .usuario(usuario)
        .fechaInicio(new Date())
        // No se está validando la fecha fin de la suscripcion ya que todo es gratuito actualmente
        .fechaFin(new Date())
        .precioTotal(BigDecimal.valueOf(4.99))
        .metodoPago("Tarjeta")
        .estadoPago("Activo")
        .build();
    
    try {
      LOGGER.info("Se procede almacenar la suscripción del usuario: {}", usuario.getNombreUsuario());
      suscripcionService.guardarSuscripcion(suscripcion);
      LOGGER.info("Suscripción almacenada exitosamente");
    } catch (Exception e) {
      LOGGER.error("Error al guardar la Suscripción para el usuariuo: {}", usuario.getNombreUsuario());
      throw new UsuarioException("Error al guardar la Suscripción para el usuariuo:");
    }
    
    LOGGER.info("Recuperando los planes de pago gratuitos por defecto");
    PlanPago planPago = planPagoService.findByIdPlanPago(1);
    
    UsuarioPlanPago usuaPlanPago = UsuarioPlanPago.builder()
        .suscripcion(suscripcion)
        .planPago(planPago)
        .reservasRestantes(planPago.getLimiteReservas())
        .fechaRenovacion(new Date())
        .build();
    
    try {
      LOGGER.info("Se procede almacenar el plan pago del usuario");
      usuarioPlanPagoService.guardarPlanPago(usuaPlanPago);
      LOGGER.info("Plan pago del usuario {} almacenado exitosamente", usuario.getNombreUsuario());
    } catch (Exception e) {
      throw new UsuarioException("Error al almacenar el plan pago del usuario: " + usuario.getNombreUsuario());
    }
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

  @Override
  public void actualizarUsuario(UsuarioDto usuarioDto) throws UsuarioException {
    try {
       LOGGER.info("Actualizando usuario");
       usuariosService.actualizarUsuario(usuarioDto.getEmail(),usuarioDto.getDireccion(), usuarioDto.getProvincia(), 
           usuarioDto.getMunicipio(), usuarioDto.getCodigoPostal(), usuarioDto.getPais(), usuarioDto.getNumeroTelefono(), usuarioDto.getIdUsuario());
       LOGGER.info("Usuario actualizado exitosamente");
    } catch (Exception e) {
      throw new UsuarioException(e);
    }
  }

  @Override
  public UsuarioDto localizarUsuario(long idUsuario) throws UsuarioException {
    return IUsuarioMapStruct.mapper.usuarioToUsuarioDto(usuariosService.findById(idUsuario).orElse(null));
  }
  
}

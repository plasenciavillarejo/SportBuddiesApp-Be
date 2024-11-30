package es.sport.buddies.main.app.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.dto.ConfirmarAsistenciaDto;
import es.sport.buddies.entity.app.models.entity.ConfirmacionUsuario;
import es.sport.buddies.entity.app.models.entity.ReservaActividad;
import es.sport.buddies.entity.app.models.entity.Usuario;
import es.sport.buddies.entity.app.models.service.IConfirmacionUsuarioService;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.ConfirmarAsistenciaException;
import es.sport.buddies.main.app.service.IConfirmarAsistenciaMainService;
import es.sport.buddies.main.app.utils.Utilidades;

@Service
public class ConfirmarAsistenciaMainServiceImpl implements IConfirmarAsistenciaMainService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaUsuarioService usuarioService;
  
  @Autowired
  private Utilidades utilidades;
  
  @Autowired
  private IConfirmacionUsuarioService confirmacionUsuarioService;
  
  @Override
  public Map<String, Object> listarUsuariosConfirmados(ConfirmarAsistenciaDto confirmarAsistenciaDto) throws ConfirmarAsistenciaException {
    Map<String, Object> params = new HashMap<>();
    Pageable page = null;
    try {
      page = utilidades.configurarPageRequest(confirmarAsistenciaDto.getCaracteristicasPaginacion().getPagina(), 
          confirmarAsistenciaDto.getCaracteristicasPaginacion().getTamanioPagina(), 
          confirmarAsistenciaDto.getCaracteristicasPaginacion().getOrden(), 
          confirmarAsistenciaDto.getCaracteristicasPaginacion().getCampoOrden());
    } catch (Exception e) {
      throw new ConfirmarAsistenciaException(e);
    }
    Page<Object[]> resActividad = usuarioService.listaConfirmacion(confirmarAsistenciaDto.getIdUsuario(), page);
    if(resActividad != null) {
      params.put("listAsistencia", resActividad.getContent().stream().map(act -> {
        ConfirmarAsistenciaDto con = new ConfirmarAsistenciaDto();
        con.setFechaReserva(LocalDate.parse(String.valueOf(act[0]), 
            DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        con.setHoraInicio(LocalTime.parse(String.valueOf(act[1])));
        con.setHoraFin(LocalTime.parse(String.valueOf(act[2])));
        con.setNombreUsuario(String.valueOf(act[3]));
        con.setApellidoUsuario(String.valueOf(act[4]));
        con.setIdUsuario(Long.valueOf(String.valueOf(act[5])));
        con.setIdReservaActividad(Long.valueOf(String.valueOf(act[6])));
        return con;
      }).toList());
      params.put("paginador", utilidades.configPaginator(page, resActividad));
    }
    return params;
  }

  @Override
  public void almacenarConfirmacionUsuario(ConfirmarAsistenciaDto confirmacionAsistenciaDto) throws ConfirmarAsistenciaException {
    try {
      LOGGER.info("Se procede a confirmar el usuario con id: {} y su actividad con id: {}", confirmacionAsistenciaDto.getIdUsuario(), confirmacionAsistenciaDto.getIdReservaActividad());
      ConfirmacionUsuario confiUsuario = ConfirmacionUsuario.builder()
          .usuario(Usuario.builder().idUsuario(confirmacionAsistenciaDto.getIdUsuario()).build())
          .reservaActividad(ReservaActividad.builder().idReservaActividad(confirmacionAsistenciaDto.getIdReservaActividad()).build())
          .build();
      confirmacionUsuarioService.guardarConfirmacionUsuario(confiUsuario);
      LOGGER.info("Se ha almacenado exitosamente");
    } catch (Exception e) {
      throw new ConfirmarAsistenciaException(e);
    }
    
  }

  @Override
  public List<Long> listIdConfirmados(long idUsuario) {
    return confirmacionUsuarioService.listarIdsUsuariosConfirmados(idUsuario);
  }

}

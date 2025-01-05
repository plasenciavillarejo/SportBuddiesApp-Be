package es.sport.buddies.main.app.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.dto.ConfirmarUsuarioDto;
import es.sport.buddies.entity.app.dto.UsuariosConfirmadosDto;
import es.sport.buddies.entity.app.models.entity.ConfirmacionUsuario;
import es.sport.buddies.entity.app.models.service.IConfirmacionUsuarioService;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.convert.map.struct.IConfirmacionUsuarioMapStruct;
import es.sport.buddies.main.app.exceptions.ConfirmarAsistenciaException;
import es.sport.buddies.main.app.service.IConfirmarUsuarioMainService;
import es.sport.buddies.main.app.utils.Utilidades;

@Service
public class ConfirmarUsuarioMainServiceImpl implements IConfirmarUsuarioMainService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaUsuarioService usuarioService;
  
  @Autowired
  private Utilidades utilidades;
  
  @Autowired
  private IConfirmacionUsuarioService confirmacionUsuarioService;
  
  @Override
  public Map<String, Object> listarUsuariosConfirmados(ConfirmarUsuarioDto ConfirmarUsuarioDto) throws ConfirmarAsistenciaException {
    Map<String, Object> params = new HashMap<>();
    Pageable page = null;
    try {
      page = utilidades.configurarPageRequest(ConfirmarUsuarioDto.getCaracteristicasPaginacion().getPagina(), 
          ConfirmarUsuarioDto.getCaracteristicasPaginacion().getTamanioPagina(), 
          ConfirmarUsuarioDto.getCaracteristicasPaginacion().getOrden(), 
          ConfirmarUsuarioDto.getCaracteristicasPaginacion().getCampoOrden());
    } catch (Exception e) {
      throw new ConfirmarAsistenciaException(e);
    }
    Page<Object[]> resActividad = usuarioService.listaConfirmacion(ConfirmarUsuarioDto.getIdUsuario(), page);
    if(resActividad != null) {
      
      List<ConfirmarUsuarioDto> confUsuDto =  resActividad.getContent().stream().map(act -> {
        ConfirmarUsuarioDto con = new ConfirmarUsuarioDto();
        con.setFechaReserva(LocalDate.parse(String.valueOf(act[0]), 
            DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        con.setHoraInicio(LocalTime.parse(String.valueOf(act[1])));
        con.setHoraFin(LocalTime.parse(String.valueOf(act[2])));
        con.setNombreUsuario(String.valueOf(act[3]));
        con.setApellidoUsuario(String.valueOf(act[4]));
        con.setIdUsuario(Long.valueOf(String.valueOf(act[5])));
        con.setIdReservaActividad(Long.valueOf(String.valueOf(act[6])));
        con.setActividad(String.valueOf(act[7]));
        return con;
      }).toList();
      
      // Agrupamos en primer lugar por actividad y fecha, posteriormente creamos otro map donde su key sera la hora y su value, todos los usuarios que este inscritos a esa misma actividad.
      Map<String, Map<LocalTime, List<ConfirmarUsuarioDto>>> agrupados = confUsuDto.stream()
          .collect(Collectors.groupingBy(u -> u.getActividad() + " | " + u.getFechaReserva(),
              Collectors.groupingBy(u -> u.getHoraInicio())
          ));
      
      params.put("listAsistencia", agrupados);
      params.put("paginador", utilidades.configPaginator(page, resActividad));
    }
    return params;
  }

  @Override
  public void almacenarConfirmacionUsuario(ConfirmarUsuarioDto confirmacionAsistenciaDto) throws ConfirmarAsistenciaException {
    try {
      LOGGER.info("Se procede a confirmar el usuario con id: {} y su actividad con id: {}", confirmacionAsistenciaDto.getIdUsuario(), 
          confirmacionAsistenciaDto.getIdReservaActividad());
      confirmacionUsuarioService.guardarConfirmacionUsuario(IConfirmacionUsuarioMapStruct.mapper.confimacionUsuarioDtoToEntity(confirmacionAsistenciaDto));
      LOGGER.info("Se ha almacenado exitosamente");
    } catch (Exception e) {
      throw new ConfirmarAsistenciaException(e);
    }
  }

  /**
   * Función encargada de retornar los usuaris confirmado con la fecha >= al día actual, En caso de que sea 
   * menor, devolverá un array vacío.
   */
  @Override
  public List<UsuariosConfirmadosDto> listIdConfirmados(long idUsuario) {
    return confirmacionUsuarioService.listarIdsUsuariosConfirmados(idUsuario).stream().map(usu -> {
      return UsuariosConfirmadosDto.builder().idUsuario(Long.valueOf(String.valueOf(usu[0])))
          .fechaReserva(LocalDate.parse(String.valueOf(usu[1])))
          .horaInicio(LocalTime.parse(String.valueOf(usu[2])))
          .horaFin(LocalTime.parse(String.valueOf(usu[3])))
          .actividad(String.valueOf(usu[4]))
          .build();
    }).toList();
  }

}

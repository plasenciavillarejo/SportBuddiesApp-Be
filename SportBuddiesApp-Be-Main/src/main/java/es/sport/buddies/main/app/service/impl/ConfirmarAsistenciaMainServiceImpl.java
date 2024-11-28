package es.sport.buddies.main.app.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.dto.ConfirmarAsistenciaDto;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;
import es.sport.buddies.main.app.service.IConfirmarAsistenciaMainService;
import es.sport.buddies.main.app.utils.Utilidades;

@Service
public class ConfirmarAsistenciaMainServiceImpl implements IConfirmarAsistenciaMainService {

  @Autowired
  private IReservaUsuarioService usuarioService;
  
  @Autowired
  private Utilidades utilidades;
  
  @Override
  public Map<String, Object> listarUsuariosConfirmados(long idUsuario) {
    Map<String, Object> params = new HashMap<>();
    Pageable page = null;
    try {
      page = utilidades.configurarPageRequest(1, 5, 1, "hora_inicio_reserva");
    } catch (Exception e) {
      // TODO: handle exception
    }
    Page<Object[]> resActividad = usuarioService.listaConfirmacion(idUsuario, page);
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
        return con;
      }).toList());
      params.put("paginador", utilidades.configPaginator(page, resActividad));
    }
    return params;
  }

}

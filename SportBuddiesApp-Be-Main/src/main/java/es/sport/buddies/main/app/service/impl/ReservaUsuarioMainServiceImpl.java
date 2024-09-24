package es.sport.buddies.main.app.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.sport.buddies.entity.app.dto.DeporteDto;
import es.sport.buddies.entity.app.dto.ReservaUsuarioDto;
import es.sport.buddies.entity.app.dto.UsuarioDto;
import es.sport.buddies.entity.app.models.service.IDeporteService;
import es.sport.buddies.entity.app.models.service.IMunicipioService;
import es.sport.buddies.entity.app.models.service.IProvinciaService;
import es.sport.buddies.entity.app.models.service.IReservaUsuarioService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaUsuarioMainService;

@Service
public class ReservaUsuarioMainServiceImpl implements IReservaUsuarioMainService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaUsuarioService reservaService;

  @Autowired
  private IDeporteService deporteServcie;
  
  @Autowired
  private IProvinciaService provinciaService;
  
  @Autowired
  private IMunicipioService muncipioService;
  
  @Override
  public List<ReservaUsuarioDto> listarReservas(LocalDate fechaReserva) throws ReservaException {
    List<ReservaUsuarioDto> res = null;
    try {
      LOGGER.info("Se procede a listar las reservas con fecha: {}", fechaReserva);
      res = reservaService.buscarReservaPorFechaAndIdUsuario(fechaReserva).stream().map(resUsu -> { 
        ReservaUsuarioDto usuDto = new ReservaUsuarioDto();
        
        usuDto.setIdReserva(resUsu.getIdReserva());
        usuDto.setFechaReserva(resUsu.getFechaReserva());
        usuDto.setHoraInicioReserva(resUsu.getHoraInicioReserva());
        usuDto.setHoraFinReserva(resUsu.getHoraFinReserva());
        usuDto.setObservaciones(resUsu.getObservaciones());
        
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setIdUsuario(resUsu.getUsuarioReserva().getIdUsuario());
        usuarioDto.setNombreUsuario(resUsu.getUsuarioReserva().getNombreUsuario());
        usuDto.setUsuarioReservaDto(usuarioDto);
        
        DeporteDto deporteDto = new DeporteDto();
        deporteDto.setActividad(resUsu.getDeporteReserva().getActividad());
        deporteDto.setIdDeporte(resUsu.getDeporteReserva().getIdDeporte());
        usuDto.setDeporteReservaDto(deporteDto);
        
        return usuDto;
      }).toList();
      LOGGER.info(!res.isEmpty() ? "Se ha encontrado un total de {} reservas" : "No se ha encontrado ningúna reserva, se devuelve un listado vacío", res.size());
    } catch (Exception e) {
      throw new ReservaException("Error en la busqueda de la reserva");
    }
    return !res.isEmpty() ? res : Collections.emptyList();
  }

  @Override
  public Map<String, Object> listarCombosPaginaInicial() throws ReservaException {
	  Map<String, Object> mapResult = new HashMap<>();
	  mapResult.put("listadoDeportes", deporteServcie.listarDeportes());
	  mapResult.put("listaProvincias", provinciaService.listarProvincias());
	  return mapResult;
  }

  @Override
  public List<String> listaMunicipiosProProvinca(String nombreProvincia) {
    return muncipioService.findByProvincia_NombreProvincia(nombreProvincia)
        .stream().map(provin -> provin.getNombreMunicipio()).toList();
  }
  
}

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

import es.sport.buddies.entity.app.models.entity.Reserva;
import es.sport.buddies.entity.app.models.service.IDeporteService;
import es.sport.buddies.entity.app.models.service.IMunicipioService;
import es.sport.buddies.entity.app.models.service.IProvinciaService;
import es.sport.buddies.entity.app.models.service.IReservaService;
import es.sport.buddies.main.app.constantes.ConstantesMain;
import es.sport.buddies.main.app.exceptions.ReservaException;
import es.sport.buddies.main.app.service.IReservaMainService;

@Service
public class ReservaMainServiceImpl implements IReservaMainService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantesMain.LOGGUERMAIN);
  
  @Autowired
  private IReservaService reservaService;

  @Autowired
  private IDeporteService deporteServcie;
  
  @Autowired
  private IProvinciaService provinciaService;
  
  @Autowired
  private IMunicipioService muncipioService;
  
  @Override
  public List<Reserva> listarReservas(LocalDate fechaReserva) throws ReservaException {
    List<Reserva> res = null;
    try {
      LOGGER.info("Se procede a listar las reservas con fecha: {}", fechaReserva);
      res = reservaService.buscarReservaPorFechaAndIdUsuario(fechaReserva);
      LOGGER.info(!res.isEmpty() ? "Se ha encontrado un total de {} reservas" : "No se ha encontrado ningúna reserva, se devuelve un listado vacío", res);
    } catch (Exception e) {
      throw new ReservaException("Error en la busqueda de la reserva");
    }
    return !res.isEmpty() ? res : Collections.emptyList();
  }

  @Override
  public Map<String, Object> listarCombosPaginaInicial() throws ReservaException {
	  Map<String, Object> mapResult = new HashMap<String, Object>();
	  mapResult.put("listadoDeportes", deporteServcie.listarDeportes());
	  mapResult.put("listaProvincias", provinciaService.listarProvincias());
	  mapResult.put("listaMunicipios", muncipioService.findByProvincia_NombreProvincia("Araba/Álava"));
	  return mapResult;
  }
	  
  
  
}

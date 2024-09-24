package es.sport.buddies.main.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import es.sport.buddies.entity.app.dto.ListadoReservaActividadDto;
import es.sport.buddies.entity.app.dto.ReservaActividadDto;
import es.sport.buddies.entity.app.dto.ReservaUsuarioDto;
import es.sport.buddies.main.app.exceptions.ReservaException;

public interface IReservaUsuarioMainService {

  public List<ReservaUsuarioDto> listarReservas(LocalDate fechaReserva) throws ReservaException;
  
  public Map<String, Object> listarCombosPaginaInicial() throws ReservaException;
 
  public List<String> listaMunicipiosPorProvinca(String nombreProvincia);
   
}

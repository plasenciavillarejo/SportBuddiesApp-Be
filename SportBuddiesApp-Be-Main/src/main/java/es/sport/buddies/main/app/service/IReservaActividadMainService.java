package es.sport.buddies.main.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import es.sport.buddies.entity.app.models.entity.ReservaUsuario;
import es.sport.buddies.main.app.exceptions.ReservaException;

public interface IReservaActividadMainService {

  public List<ReservaUsuario> listarReservas(LocalDate fechaReserva) throws ReservaException;
  
  public Map<String, Object> listarCombosPaginaInicial() throws ReservaException;
 
  public List<String> listaMunicipiosProProvinca(String nombreProvincia);
  
}

package es.sport.buddies.main.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import es.sport.buddies.entity.app.models.entity.Municipio;
import es.sport.buddies.entity.app.models.entity.Reserva;
import es.sport.buddies.main.app.exceptions.ReservaException;

public interface IReservaMainService {

  public List<Reserva> listarReservas(LocalDate fechaReserva) throws ReservaException;
  
  public Map<String, Object> listarCombosPaginaInicial() throws ReservaException;
 
  public List<String> listaMunicipiosProProvinca(String nombreProvincia);
  
}

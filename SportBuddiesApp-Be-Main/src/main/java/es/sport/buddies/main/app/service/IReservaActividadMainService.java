package es.sport.buddies.main.app.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import es.sport.buddies.entity.app.dto.CrearReservaActividadDto;
import es.sport.buddies.entity.app.dto.ListadoReservaActividadDto;
import es.sport.buddies.entity.app.dto.ReservaActividadDto;
import es.sport.buddies.main.app.exceptions.CrearReservaException;
import es.sport.buddies.main.app.exceptions.ReservaException;

public interface IReservaActividadMainService {

  public List<ReservaActividadDto> listadoReservaActividad(ListadoReservaActividadDto listadoDto) throws ReservaException, InterruptedException, ExecutionException; 
  
  public void crearReservaActivdad(CrearReservaActividadDto reservaActividadDto) throws CrearReservaException;
  
}

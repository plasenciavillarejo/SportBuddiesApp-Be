package es.sport.buddies.main.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import es.sport.buddies.entity.app.dto.CrearReservaActividadDto;
import es.sport.buddies.entity.app.dto.InscripcionReservaActividadDto;
import es.sport.buddies.entity.app.dto.ListadoReservaActividadDto;
import es.sport.buddies.main.app.exceptions.CrearReservaException;
import es.sport.buddies.main.app.exceptions.ReservaException;

public interface IReservaActividadMainService {

  public Map<String, Object> listadoReservaActividad(ListadoReservaActividadDto listadoDto, Pageable pageable) throws ReservaException; 
  
  public void crearReservaActivdad(CrearReservaActividadDto reservaActividadDto) throws CrearReservaException;
    
  public Map<String, Object> listarCombosPaginaInicial(boolean provincias) throws ReservaException;
  
  public List<String> listaMunicipiosPorProvinca(String nombreProvincia);
  
  public void inscripcionReservaActividad(InscripcionReservaActividadDto inscripcionReservaActividad) throws ReservaException, CrearReservaException;
 
  public List<Long> listarActividadInscritas(long idUsuario);

  public Map<String, Object> listarReservaActividaPorId(long idUsuario, Pageable pageable) throws ReservaException; 
  
}

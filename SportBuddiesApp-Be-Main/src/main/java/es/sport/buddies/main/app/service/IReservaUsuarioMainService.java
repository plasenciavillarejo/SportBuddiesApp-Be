package es.sport.buddies.main.app.service;

import java.time.LocalDate;
import java.util.List;

import es.sport.buddies.entity.app.dto.ReservaUsuarioDto;
import es.sport.buddies.main.app.exceptions.CancelarReservaException;
import es.sport.buddies.main.app.exceptions.ReservaException;

public interface IReservaUsuarioMainService {

  public List<ReservaUsuarioDto> listarReservas(LocalDate fechaReserva, long idUsuariom, boolean historial) throws ReservaException;
     
  public void eliminarActividad(long idReservaUsuario, long idUsuario) throws CancelarReservaException;
  
  public double obtenerPrecioActividad(long idReservaUsuario) throws CancelarReservaException;
    
}

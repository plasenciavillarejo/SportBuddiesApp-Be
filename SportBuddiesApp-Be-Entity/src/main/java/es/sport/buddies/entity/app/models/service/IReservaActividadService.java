package es.sport.buddies.entity.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ReservaActividad;

public interface IReservaActividadService {

  public List<ReservaActividad> findByFechaReservaAndActividadAndProvinciaAndMunicipio(@Param("fechaReserva") Date fechaReserva,
      @Param("actividad") String actividad, @Param("provincia") String provincia, @Param("municipio") String municipio);
  
}

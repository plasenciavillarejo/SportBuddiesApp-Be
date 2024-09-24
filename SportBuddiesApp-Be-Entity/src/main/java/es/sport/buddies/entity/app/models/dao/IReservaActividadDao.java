package es.sport.buddies.entity.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ReservaActividad;

public interface IReservaActividadDao extends JpaRepository<ReservaActividad, Long> {

  public List<ReservaActividad> findByFechaReservaAndActividadAndProvinciaAndMunicipio(@Param("fechaReserva") Date fechaReserva,
      @Param("actividad") String actividad, @Param("provincia") String provincia, @Param("municipio") String municipio);
  
}

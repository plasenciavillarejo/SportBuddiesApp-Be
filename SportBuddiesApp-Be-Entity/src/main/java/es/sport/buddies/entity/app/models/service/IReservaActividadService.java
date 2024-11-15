package es.sport.buddies.entity.app.models.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ReservaActividad;

public interface IReservaActividadService {

  public Page<ReservaActividad> findByFechaReservaAndActividadAndProvinciaAndMunicipio(@Param("fechaReserva") Date fechaReserva,
      @Param("actividad") String actividad, @Param("provincia") String provincia, @Param("municipio") String municipio, Pageable pageable);
  
  public void guardarReservaActividad(ReservaActividad reservaActividadDto);
  
  public ReservaActividad findByProvinciaAndMunicipioAndFechaReservaAndHoraInicioAndHoraFinAndUsuarioActividad_IdUsuario(
      @Param("provincia") String provincia, @Param("muncipio") String municpio,
      @Param("fechaReserva") LocalDate fechaReserva, @Param("horaInicio") LocalTime horaInicio,
      @Param("horaFIn") LocalTime horaFin, @Param("idUsuario") long idUsuario);
  
  public Page<ReservaActividad> findByUsuarioActividad_IdUsuario(@Param("idUsuario") long idUsuario, Pageable pageable);
  
  public ReservaActividad findById(@Param("idReservaActividad") long idReservaActividad);
  
  public void actualizarPlazaRestantes(@Param("idReservaActividad") long idReservaActividad, @Param("plazaRestante") long plazaRestante);
  
}

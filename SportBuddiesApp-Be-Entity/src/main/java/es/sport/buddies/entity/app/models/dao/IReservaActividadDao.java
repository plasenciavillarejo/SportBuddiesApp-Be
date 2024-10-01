package es.sport.buddies.entity.app.models.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ReservaActividad;

public interface IReservaActividadDao extends JpaRepository<ReservaActividad, Long> {

  @EntityGraph(attributePaths = {"usuarioActividad"})
  public List<ReservaActividad> findByFechaReservaAndActividadAndProvinciaAndMunicipio(@Param("fechaReserva") Date fechaReserva,
      @Param("actividad") String actividad, @Param("provincia") String provincia, @Param("municipio") String municipio);
 
  public ReservaActividad findByProvinciaAndMunicipioAndFechaReservaAndHoraInicioAndHoraFinAndUsuarioActividad_IdUsuario(
      @Param("provincia") String provincia, @Param("muncipio") String municpio,
      @Param("fechaReserva") LocalDate fechaReserva, @Param("horaInicio") LocalTime horaInicio,
      @Param("horaFIn") LocalTime horaFin, @Param("idUsuario") long idUsuario);

}

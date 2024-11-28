package es.sport.buddies.entity.app.models.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.ReservaActividad;

public interface IReservaActividadDao extends JpaRepository<ReservaActividad, Long>, PagingAndSortingRepository<ReservaActividad, Long> {

  public Page<ReservaActividad> findByFechaReservaAndActividadAndProvinciaAndMunicipioAndUsuarioActividad_IdUsuarioNot(@Param("fechaReserva") Date fechaReserva,
      @Param("actividad") String actividad, @Param("provincia") String provincia, @Param("municipio") String municipio,
      @Param("idUsuario") long idUsuario, Pageable pageable);
  
  @EntityGraph(attributePaths = {"usuarioActividad"})
  public Page<ReservaActividad> findByFechaReservaAndActividadAndProvinciaAndMunicipio(@Param("fechaReserva") Date fechaReserva,
      @Param("actividad") String actividad, @Param("provincia") String provincia, @Param("municipio") String municipio, Pageable pageable);
 
  public ReservaActividad findByProvinciaAndMunicipioAndFechaReservaAndHoraInicioAndHoraFinAndUsuarioActividad_IdUsuario(
      @Param("provincia") String provincia, @Param("muncipio") String municpio,
      @Param("fechaReserva") LocalDate fechaReserva, @Param("horaInicio") LocalTime horaInicio,
      @Param("horaFIn") LocalTime horaFin, @Param("idUsuario") long idUsuario);
 
  @EntityGraph(attributePaths = {"usuarioActividad"})
  public Page<ReservaActividad> findByUsuarioActividad_IdUsuario(@Param("idUsuario") long idUsuario,Pageable pageable);
  
  @Modifying
  @Query(value = "update ReservaActividad set plazasRestantes = :plazaRestante where idReservaActividad = :idReservaActividad")
  public void actualizarPlazaRestantes(@Param("idReservaActividad") long idReservaActividad, @Param("plazaRestante") long plazaRestante);
  
}

package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import es.sport.buddies.entity.app.models.entity.ReservaActividad;

public interface IReservaActividadDao extends JpaRepository<ReservaActividad, Long> {

}

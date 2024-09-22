package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import es.sport.buddies.entity.app.models.entity.Deporte;

public interface IDeporteDao extends JpaRepository<Deporte, Long>{
	
}

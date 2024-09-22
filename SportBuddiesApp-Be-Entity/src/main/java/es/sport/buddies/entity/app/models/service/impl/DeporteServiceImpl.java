package es.sport.buddies.entity.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IDeporteDao;
import es.sport.buddies.entity.app.models.entity.Deporte;
import es.sport.buddies.entity.app.models.service.IDeporteService;

@Service
public class DeporteServiceImpl implements IDeporteService {

	@Autowired
	private IDeporteDao deporteDao;

	@Override
	@Transactional(readOnly = true)
	public List<Deporte> listarDeportes() {
		return deporteDao.findAll();
	}
	
	
	
}

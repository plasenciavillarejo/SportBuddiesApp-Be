package es.sport.buddies.entity.app.models.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IPagoTarjetaDao;
import es.sport.buddies.entity.app.models.entity.PagoTarjeta;
import es.sport.buddies.entity.app.models.service.IPagoTarjetaService;

@Service
public class PagoTarjetaServiceImpl implements IPagoTarjetaService {

  @Autowired
  private IPagoTarjetaDao pagoTarjetaDao;
  
  @Override
  @Transactional
  public void guardarPagoTarjeta(PagoTarjeta pagoTarjeta) {
    pagoTarjetaDao.save(pagoTarjeta);
  }

  @Override
  @Transactional(readOnly = true)
  public PagoTarjeta findByIdDevolucion(String idDevolucion) {
    return pagoTarjetaDao.findByIdDevolucion(idDevolucion);
  }

  @Override
  @Transactional
  public void actualizarPagoReembolso(LocalDate fechaDevolucion, String idDevolucion) {
    pagoTarjetaDao.actualizarPagoReembolso(fechaDevolucion, idDevolucion);
  }

}

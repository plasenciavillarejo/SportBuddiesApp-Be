package es.sport.buddies.entity.app.models.service;

import java.time.LocalDate;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.PagoTarjeta;

public interface IPagoTarjetaService {

  public void guardarPagoTarjeta(PagoTarjeta pagoTarjeta);
  
  public PagoTarjeta findByIdDevolucion(@Param("idDevolucion") String idDevolucion);
  
  public void actualizarPagoReembolso(@Param("fechaDevolucion") LocalDate fechaDevolucion, @Param("idDevolucion") String idDevolucion);
  
}

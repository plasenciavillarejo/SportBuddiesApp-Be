package es.sport.buddies.entity.app.models.service;

import java.time.LocalDate;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.Paypal;

public interface IPaypalService {

  public void guardar(Paypal paypal);
    
  public Paypal buscarReservaPagada(@Param("idReservaUsuario") long idReservaUsuario);
  
  public void actualizarReservaReembolsada(@Param("fechaReembolso") LocalDate fechaReembolso, @Param("idPaypal") long idPaypal);
    
}

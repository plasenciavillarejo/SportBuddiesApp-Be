package es.sport.buddies.entity.app.models.service;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.UsuarioPlanPago;

public interface IUsuarioPlanPagoService {

  public UsuarioPlanPago findBySuscripcion_IdSuscripcion(@Param("idSuscripcion") long idSuscripcion);
  
  public void actualizarReservasRestantes(@Param("reservasRestantes") long reservasRestantes, @Param("idPlanPago") long idPlanPago);
  
}

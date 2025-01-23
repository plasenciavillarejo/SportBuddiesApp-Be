package es.sport.buddies.entity.app.models.service;

import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.PlanPago;

public interface IPlanPagoService {

  public PlanPago findByIdPlanPago(@Param("idPlan") long idPlan);
  
}

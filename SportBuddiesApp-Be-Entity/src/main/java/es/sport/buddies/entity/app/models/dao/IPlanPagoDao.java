package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.PlanPago;

public interface IPlanPagoDao extends JpaRepository<PlanPago, Long> {

  public PlanPago findByIdPlanPago(@Param("idPlan") long idPlan);
  
}

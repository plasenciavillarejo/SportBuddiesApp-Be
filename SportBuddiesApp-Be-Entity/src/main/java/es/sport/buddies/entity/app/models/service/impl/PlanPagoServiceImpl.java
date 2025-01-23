package es.sport.buddies.entity.app.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.sport.buddies.entity.app.models.dao.IPlanPagoDao;
import es.sport.buddies.entity.app.models.entity.PlanPago;
import es.sport.buddies.entity.app.models.service.IPlanPagoService;

@Service
public class PlanPagoServiceImpl implements IPlanPagoService {

  @Autowired
  private IPlanPagoDao planPagoDao;
  
  @Override
  @Transactional(readOnly = true)
  public PlanPago findByIdPlanPago(long idPlan) {
    return planPagoDao.findByIdPlanPago(idPlan);
  }

  
  
}

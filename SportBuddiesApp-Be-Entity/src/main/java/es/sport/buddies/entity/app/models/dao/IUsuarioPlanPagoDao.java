package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.UsuarioPlanPago;

public interface IUsuarioPlanPagoDao extends JpaRepository<UsuarioPlanPago, Long> {

  @EntityGraph(attributePaths = {"planPago"})
  public UsuarioPlanPago findBySuscripcion_IdSuscripcion(@Param("idSuscripcion") long idSuscripcion);
  
  @Modifying
  @Query(value = "UPDATE UsuarioPlanPago SET reservasRestantes = :reservasRestantes WHERE idUsuarioPlanPago = :idPlanPago")
  public void actualizarReservasRestantes(@Param("reservasRestantes") long reservasRestantes, @Param("idPlanPago") long idPlanPago);
  
}

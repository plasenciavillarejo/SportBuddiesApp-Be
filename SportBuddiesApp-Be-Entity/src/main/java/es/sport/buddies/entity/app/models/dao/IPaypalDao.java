package es.sport.buddies.entity.app.models.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.Paypal;

public interface IPaypalDao extends JpaRepository<Paypal, Long> {

  @EntityGraph(attributePaths = { "reservaUsuario" })
  @Query(value = "from Paypal p where p.reservaUsuario.idReserva = :idReservaUsuario")
  public Paypal buscarReservaPagada(@Param("idReservaUsuario") long idReservaUsuario);
  
  @Modifying
  @Query(value = "update Paypal set reembolsado = true, fechaReembolso = :fechaReembolso,"
      + " reservaUsuario = null where idPaypal = :idPaypal")
  public void actualizarReservaReembolsada(@Param("fechaReembolso") LocalDate fechaReembolso,@Param("idPaypal") long idPaypal);
    
}

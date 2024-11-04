package es.sport.buddies.entity.app.models.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.sport.buddies.entity.app.models.entity.PagoTarjeta;

public interface IPagoTarjetaDao extends JpaRepository<PagoTarjeta, Long> {

  public PagoTarjeta findByIdDevolucion(@Param("idDevolucion") String idDevolucion);
  
  @Modifying
  @Query(value = "update PagoTarjeta set fechaDevolucion = :fechaDevolucion, reembolsado = true"
      + " where idDevolucion = :idDevolucion")
  public void actualizarPagoReembolso(@Param("fechaDevolucion") LocalDate fechaDevolucion, @Param("idDevolucion") String idDevolucion);
  
}

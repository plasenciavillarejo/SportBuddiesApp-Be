package es.sport.buddies.entity.app.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class SuscripcionDto {

  private long idSuscripcion;

  private UsuarioDto usuarioDto;

  private Date fechaInicio;

  private Date fechaFin;

  private BigDecimal precioTotal;

  private String metodoPago;

  private String estadoPago;

}

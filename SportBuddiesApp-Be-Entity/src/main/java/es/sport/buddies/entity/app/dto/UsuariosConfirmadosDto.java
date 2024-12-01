package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuariosConfirmadosDto implements Serializable {

  private long idUsuario;

  private LocalDate fechaReserva;

  private LocalTime horaInicio;

  private LocalTime horaFin;

  private static final long serialVersionUID = -5415770705532731925L;

}

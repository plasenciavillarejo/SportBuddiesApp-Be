package es.sport.buddies.main.app.exceptions;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import es.sport.buddies.entity.app.dto.AvisoErrorDto;

@ControllerAdvice
public class SportBuddieMainExceptionHandler {

  @ExceptionHandler(ReservaException.class)
  public ResponseEntity<Object> handleControlExceptions(ReservaException ex) {
    String [] separaCadena = ex.getCause() != null ? ex.getCause().toString().split(":") : null;
    AvisoErrorDto avisoErro = AvisoErrorDto.builder().localDate(new Date())
        .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .mensaje(ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : separaCadena[1])
        .causa(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        .stack(ExceptionUtils.getStackTrace(ex))
        .build();
    return new ResponseEntity<>(avisoErro, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  @ExceptionHandler(CrearReservaException.class)
  public ResponseEntity<Object> handleControlExceptions(CrearReservaException ex) {
    String [] separaCadena = ex.getCause() != null ? ex.getCause().toString().split(":") : null;
    AvisoErrorDto avisoErro = AvisoErrorDto.builder().localDate(new Date())
        .codigo(HttpStatus.CONFLICT.value())
        .mensaje(separaCadena[1])
        .causa(HttpStatus.CONFLICT.getReasonPhrase())
        .build();
    return new ResponseEntity<>(avisoErro, HttpStatus.CONFLICT);
  }
   
  @ExceptionHandler(CancelarReservaException.class)
  public ResponseEntity<Object> handleCancelReservaExceptions(CancelarReservaException ex) {
    String [] separaCadena = ex.getCause() != null ? ex.getCause().toString().split(":") : null;
    AvisoErrorDto avisoErro = AvisoErrorDto.builder().localDate(new Date())
        .codigo(HttpStatus.CONFLICT.value())
        .mensaje(separaCadena[2])
        .causa(HttpStatus.CONFLICT.getReasonPhrase())
        .build();
    return new ResponseEntity<>(avisoErro, HttpStatus.CONFLICT);
  }
    
  @ExceptionHandler(PaypalException.class)
  public ResponseEntity<Object> handlePaypalExceptions(PaypalException ex) {
    String [] separaCadena = ex.getCause() != null ? ex.getCause().toString().split(":") : null;
    AvisoErrorDto avisoErro = AvisoErrorDto.builder().localDate(new Date())
        .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .mensaje(separaCadena[1])
        .causa(separaCadena[1])
        .stack(ExceptionUtils.getStackTrace(ex))
        .build();
    return new ResponseEntity<>(avisoErro, HttpStatus.INTERNAL_SERVER_ERROR);
  }
 
  @ExceptionHandler(UsuarioException.class)
  public ResponseEntity<Object> handleUsuarioExceptions(UsuarioException ex) {
    String [] separaCadena = ex.getCause() != null ? ex.getCause().toString().split(":") : null;
    AvisoErrorDto avisoErro = AvisoErrorDto.builder().localDate(new Date())
        .codigo(HttpStatus.CONFLICT.value())
        .mensaje(separaCadena[1])
        .causa(HttpStatus.CONFLICT.getReasonPhrase())
        .build();
    return new ResponseEntity<>(avisoErro, HttpStatus.CONFLICT);
  }
  
  @ExceptionHandler(PagoTarjetaException.class)
  public ResponseEntity<Object> handlePagoTarjetaExceptions(PagoTarjetaException ex) {
    String [] separaCadena = ex.getCause() != null ? ex.getCause().toString().split(":") : null;
    AvisoErrorDto avisoErro = AvisoErrorDto.builder().localDate(new Date())
        .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .mensaje(separaCadena[1])
        .causa(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        .build();
    return new ResponseEntity<>(avisoErro, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  @ExceptionHandler(ConfirmarAsistenciaException.class)
  public ResponseEntity<Object> handleConfirmarAsistenciaExceptions(ConfirmarAsistenciaException ex) {
    String [] separaCadena = ex.getCause() != null ? ex.getCause().toString().split(":") : null;
    AvisoErrorDto avisoErro = AvisoErrorDto.builder().localDate(new Date())
        .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .mensaje(separaCadena[1])
        .causa(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        .build();
    return new ResponseEntity<>(avisoErro, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
}

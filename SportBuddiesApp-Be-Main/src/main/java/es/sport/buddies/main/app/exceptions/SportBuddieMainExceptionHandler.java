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
    AvisoErrorDto avisoErro = AvisoErrorDto.builder().localDate(new Date())
        .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .mensaje(ex.getMessage())
        .causa(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        .stack(ExceptionUtils.getStackTrace(ex))
        .build();
    return new ResponseEntity<>(avisoErro, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
}

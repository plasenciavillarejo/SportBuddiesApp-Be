package es.sport.buddies.entity.app.dto;

import org.springframework.security.core.Authentication;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class DobleFactorDto {

  private Authentication authentication;
  
}

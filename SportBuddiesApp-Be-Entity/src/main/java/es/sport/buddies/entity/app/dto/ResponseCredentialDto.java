package es.sport.buddies.entity.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseCredentialDto {

  private String attestationObject;
  
  private String clientDataJSON;
  
}

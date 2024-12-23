package es.sport.buddies.entity.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasskeyCredentialDto {

  private String id;
  
  private String rawId;
  
  private String type;
  
  private ResponseCredentialDto response;
  
  private Object clientExtensionResults;
  
  private String nombreUsuario;
  
}

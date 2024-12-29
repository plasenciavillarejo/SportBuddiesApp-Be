package es.sport.buddies.entity.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginPassKeyNavigationDto {

  private String credentialId;
  
  private String challangeGenerateBe;
  
  private String authenticatorData;
  
  private String clientDataJson;
  
  private String signature;
  
}

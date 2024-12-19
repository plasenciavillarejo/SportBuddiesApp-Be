package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasskeyDto implements Serializable {

  private String username;

  private String displayName;
  
  private String userId;
  
  private String rpId;
  
  private String origin;
  
  private static final long serialVersionUID = 8691777191512551387L;
  
}

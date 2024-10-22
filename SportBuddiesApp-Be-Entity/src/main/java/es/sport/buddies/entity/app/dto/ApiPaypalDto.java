package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ApiPaypalDto implements Serializable {

  private String access_token;

  private static final long serialVersionUID = -858341457380759239L;

}

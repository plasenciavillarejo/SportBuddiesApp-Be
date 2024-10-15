package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ClientesOauthDto implements Serializable {

  private long idClienteOauth;

  private String clientId;

  private String clientSecret;

  private String clientName;
  
  private List<String> authenticationMethods;

  private List<String> authorizationGrantTypes;

  private List<String> redirectUris;

  private List<String> postLogoutRedirectUris;

  private List<String> scopes;

  private long timeAccesToken;
  
  private long timeRefrehsToken;

  private static final long serialVersionUID = -6406930566061300940L;

}

package es.sport.buddies.entity.app.dto;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ClientesOauthDto implements Serializable {

  private long idClienteOauth;

  private String clientId;

  private String clientSecret;

  private String clientName;
  
  private Set<String> authenticationMethods;

  private Set<String> authorizationGrantTypes;

  private Set<String> redirectUris;

  private Set<String> postLogoutRedirectUris;

  private Set<String> scopes;

  private long timeAccesToken;
  
  private long timeRefrehsToken;

  private static final long serialVersionUID = -6406930566061300940L;

}

package es.sport.buddies.oauth.app.constantes;

import org.springframework.stereotype.Component;

import es.sport.buddies.entity.app.dto.UsernameAuthenticationDto;

@Component
public class ConstantesApp {

  private ConstantesApp() {
    // Constructor Vacío
  }
  
  public static String FICHERPEMPLUBLIKEY = "KEY";

  public static String RSA = "SEGURIDAD";
  
  public static String REDIRECTURIOAUTH2 = "REDIRECT";
  
  public static String ENDPOINTAUTHORIZATION = "AUTHORIZATION";
  
  public static String ENDPOINTLOGOUT = "LOGOUT";
  
  public static String CLIENTID = "ID";
  
  public static String CLIENTSECRET = "SECRET";
  
  public static final String ROLE = "ROLE_";
  
  public static String REDIRECTANGULAR = "REDIRECTANGULAR";
  
  public static String CLIENTIDANGULAR = "CLIENTIDANGULAR";
  
  public static String CLIENTSECRETANGULAR = "CLIENTSECRETANGULAR";

  public static UsernameAuthenticationDto userAuthenticationDto;

  public static final String LOGIN = "/login";
  
  public static final String LOGOUTANGULAR = "http://localhost:4200/logout";
  
  public static final String ASTERISCO = "*";
  
}

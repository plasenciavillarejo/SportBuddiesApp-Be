package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "clientes_oauth")
public class ClientesOauth  implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_cliente_oauth")
  private Long idClienteOauth;
  
  @Column(name = "client_id")
  private String clientId;
  
  @Column(name = "client_secret")
  private String clientSecret;

  @Column(name = "nombre_cliente")
  private String clientName;
  
  @Column(name = "metodos_autenticacion")
  private String authenticationMethods;
  
  @Column(name = "tipos_autorizacion")
  private String authorizationGrantTypes;
  
  @Column(name = "redireccion_uris")
  private String redirectUris;
  
  @Column(name = "redireccion_uris_logout")
  private String postLogoutRedirectUris;
  
  @Column(name = "permisos")
  private String scopes;
  
  @Column(name = "access_token")
  private long timeAccesToken;
  
  @Column(name = "refresh_token")
  private long timeRefrehsToken;

  private static final long serialVersionUID = 2414252204703266953L;
  
}

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

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario_google")
public class UsuarioGoogle implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  @Column(name = "email")
  private String email;
  
  @Column(name = "nombre")
  private String name;
  
  @Column(name = "nombre_pila")
  private String givenName;
  
  @Column(name = "apellidos")
  private String familyName;
  
  @Column(name = "url_imagen")
  private String pictureUrl;
  
  private static final long serialVersionUID = -114443314100458729L;

}

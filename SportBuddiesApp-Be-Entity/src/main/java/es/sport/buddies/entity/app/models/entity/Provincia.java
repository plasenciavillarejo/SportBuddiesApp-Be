package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "provincias")
public class Provincia implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_provincia")
  private Long idProvincia;

  @Column(name = "nombre_provincia", length = 200)
  private String nombreProvincia;

  @Column(name = "sigla_provincia", length = 10)
  private String siglaProvincia;

  public Long getIdProvincia() {
    return idProvincia;
  }

  public void setIdProvincia(Long idProvincia) {
    this.idProvincia = idProvincia;
  }

  public String getNombreProvincia() {
    return nombreProvincia;
  }

  public void setNombreProvincia(String nombreProvincia) {
    this.nombreProvincia = nombreProvincia;
  }

  public String getSiglaProvincia() {
    return siglaProvincia;
  }

  public void setSiglaProvincia(String siglaProvincia) {
    this.siglaProvincia = siglaProvincia;
  }

  private static final long serialVersionUID = 5517025627391941797L;

}

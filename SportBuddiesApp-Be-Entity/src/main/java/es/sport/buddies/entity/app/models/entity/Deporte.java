package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "deportes")
public class Deporte implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idDeporte")
  private long idDeporte;

  @Column(name = "actividad", length = 100)
  private String actividad;

  public long getIdDeporte() {
    return idDeporte;
  }

  public void setIdDeporte(long idDeporte) {
    this.idDeporte = idDeporte;
  }

  public String getActividad() {
    return actividad;
  }

  public void setActividad(String actividad) {
    this.actividad = actividad;
  }

  private static final long serialVersionUID = -8539637334356413884L;

}

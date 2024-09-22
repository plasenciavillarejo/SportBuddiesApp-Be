package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "municipios")
public class Municipio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Long idMunicipio;

    @Column(name = "nombre_municipio", length = 200)
    private String nombreMunicipio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_provincia_fk", nullable = false)
    private Provincia provincia;

    public Long getIdMunicipio() {
      return idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
      this.idMunicipio = idMunicipio;
    }

    public String getNombreMunicipio() {
      return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
      this.nombreMunicipio = nombreMunicipio;
    }

    public Provincia getProvincia() {
      return provincia;
    }

    public void setProvincia(Provincia provincia) {
      this.provincia = provincia;
    }

    private static final long serialVersionUID = -1646304863958452709L;

}
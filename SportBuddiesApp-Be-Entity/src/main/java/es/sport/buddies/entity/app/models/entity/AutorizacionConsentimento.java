package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "autorizacion_consentimiento")
@IdClass(AutorizacionConsentimento.AuthorizationConsentId.class)
public class AutorizacionConsentimento implements Serializable {
  
  @Id
  @Column(name = "id_cliente_registrado")
  private String idClienteRegistrado;
  
  @Id
  @Column(name = "nombre_principal")
  private String principalName;
  
  @Column(name = "authorities", length = 1000)
  private String authorities;

  public static class AuthorizationConsentId implements Serializable {
    
    private String idClienteRegistrado;
    
    private String principalName;

    public String getIdClienteRegistrado() {
      return idClienteRegistrado;
    }

    public void setIdClienteRegistrado(String idClienteRegistrado) {
      this.idClienteRegistrado = idClienteRegistrado;
    }

    public String getPrincipalName() {
      return principalName;
    }

    public void setPrincipalName(String principalName) {
      this.principalName = principalName;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      AuthorizationConsentId that = (AuthorizationConsentId) o;
      return idClienteRegistrado.equals(that.idClienteRegistrado) && principalName.equals(that.principalName);
    }

    @Override
    public int hashCode() {
      return Objects.hash(idClienteRegistrado, principalName);
    }
    
    private static final long serialVersionUID = 78764772205309083L;

  }
  
  private static final long serialVersionUID = 5140181307723570723L;

}

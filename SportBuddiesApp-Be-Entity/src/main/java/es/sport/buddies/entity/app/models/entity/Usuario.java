package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario")
	private long idUsuario;

	@Column(name = "nombreUsuario", length = 50)
	private String nombreUsuario;

	@Column(length = 60)
	private String password;

	private Boolean enabled;
	
	@Column(length = 50)
	private String apellido;

	@Column(unique = true, length = 50)
	private String email;

	@ManyToMany(fetch = FetchType.LAZY)
	/* Está configuración nos siver para crear una tabla intermedia entre usuario y roles que maneje ambas fk relacionadas entre ellas
	  Indicamos el nombre de la clase intermedia -> usuarios_to_roles
	   Llave foránea de la clase principal -> joinColumns = @JoincColumn
	   	name=user_id,
	   	LLave foranea de roles -> inverseJoinColumns = @JoinColumn(name="role_id")
	  Indicamos un Constraint para que el el user_id y el role_id sean únicos y no sean repetidos -> Un usuario no puede tener un rol repetido
	   	*/
	@JoinTable(name = "usuarios_in_role",
	joinColumns = @JoinColumn(name="usuario_id"),
	inverseJoinColumns = @JoinColumn(name="role_id"),
	uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","role_id"})})
	private List<Role> roles;

	//private Integer intentos;

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	/*
	public Integer getIntentos() {
		return intentos;
	}

	public void setIntentos(Integer intentos) {
		this.intentos = intentos;
	}
	*/
	private static final long serialVersionUID = 3877502676666789805L;

}

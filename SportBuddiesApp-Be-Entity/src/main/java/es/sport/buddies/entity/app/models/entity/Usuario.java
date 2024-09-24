package es.sport.buddies.entity.app.models.entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private long idUsuario;

	@Column(name = "nombre_usuario", length = 50)
	private String nombreUsuario;

	@Column(name = "password",length = 60)
	private String password;

	@Column(name = "enabled")
	private Boolean enabled;
	
	@Column(name = "apellido", length = 50)
	private String apellido;

	@Column(name = "email",unique = true, length = 50)
	private String email;

	
	/* Está configuración nos siver para crear una tabla intermedia entre usuario y roles que maneje ambas fk relacionadas entre ellas
	  Indicamos el nombre de la clase intermedia -> usuarios_to_roles
	   Llave foránea de la clase principal -> joinColumns = @JoincColumn
	   	name=user_id,
	   	LLave foranea de roles -> inverseJoinColumns = @JoinColumn(name="role_id")
	  Indicamos un Constraint para que el el user_id y el role_id sean únicos y no sean repetidos -> Un usuario no puede tener un rol repetido
	   	*/
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name = "usuarios_in_role",
	joinColumns = @JoinColumn(name="usuario_id"),
	inverseJoinColumns = @JoinColumn(name="role_id"),
	uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","role_id"})})
	private List<Role> roles;

	//private Integer intentos;

	private static final long serialVersionUID = 3877502676666789805L;

}

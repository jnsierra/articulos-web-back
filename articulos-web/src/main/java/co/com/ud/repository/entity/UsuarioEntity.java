package co.com.ud.repository.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import co.com.ud.repository.entity.enumeracion.TipoUsuario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "us_usuario", uniqueConstraints = { @UniqueConstraint(columnNames = "email", name = "UK_USUARIO_EMAIL") })
@NamedQueries({
		@NamedQuery(name = "UsuarioEntity.updateTipoUsuario", query = "UPDATE UsuarioEntity u SET u.tipoUsuario = :tipoUsuario WHERE u.id = :id") })
@Getter
@Setter
@ToString
public class UsuarioEntity extends Auditable<String> {

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_generator")
	@SequenceGenerator(name = "usuario_generator", sequenceName = "usuario_seq", allocationSize = 1)
	private Long id;
	@NotNull
	@NotBlank
	@NotEmpty
	@Column(name = "email")
	private String correo;
	@NotNull
	@NotBlank
	@NotEmpty
	@Column(name = "password")
	private String contrasena;
	@NotNull
	@NotEmpty
	@NotBlank
	@Column(name = "name")
	private String nombre;
	@NotNull
	@NotEmpty
	@NotBlank
	@Column(name = "cambio_contra")
	private String cambioContra;
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_usuario")
	private TipoUsuario tipoUsuario;
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<IdeaEntity> ideas = new ArrayList<IdeaEntity>();
	
	public void addIdea(IdeaEntity idea) {
		ideas.add(idea);
		idea.setUsuario(this);
	}
	
	public void removeIdea(IdeaEntity idea) {
		ideas.remove(idea);
		idea.setUsuario(null);
	}
}

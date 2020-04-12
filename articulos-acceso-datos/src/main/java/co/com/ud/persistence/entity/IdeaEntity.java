package co.com.ud.persistence.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.*;

@Entity
@Table(name = "id_ideas")
@NamedQueries({
	@NamedQuery(name = "IdeaEntity.buscarIdeasPorUsuario", query = "select idea from IdeaEntity idea inner join fetch idea.usuario us where us.id = :idUsuario order by idea.estado "),
	@NamedQuery(name = "IdeaEntity.buscarIdeaByProfesorAndEstado", query = "select idea from IdeaEntity idea where idea.estado = :estado and idea.idProfesor = :idProfesor order by idea.estado"),
	@NamedQuery(name = "IdeaEntity.buscarIdeaByProfesor", query = "select idea from IdeaEntity idea where idea.idProfesor = :idProfesor"),
	@NamedQuery(name = "IdeaEntity.updateEstado", query = "update IdeaEntity idea SET idea.estado = :estado, idea.idProfesorAutoriza = :idProfAut, idea.fechaAprobacion = now() WHERE idea.id = :idIdea"),
	@NamedQuery(name = "IdeaEntity.updateEstadoEspera", query = "update IdeaEntity idea SET idea.estado = 'EN_ESPERA' WHERE idea.id = :idIdea"),
	@NamedQuery(name = "IdeaEntity.getEstadoIdeas", query = "SELECT idea FROM IdeaEntity idea where idea.estado in ('RECHAZAR','CREADA','APROBAR') order by idea.estado ")
})
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class IdeaEntity extends Auditable<String>{
	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idea_generator")
	@SequenceGenerator(name = "idea_generator", sequenceName = "idea_seq", allocationSize = 1)
	private Long id;
	@Column(name = "titulo", length = 300)
	private String titulo;
	@Column(name = "contenido", length = 2500)
	private String contenido;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id",nullable = false)
	private UsuarioEntity usuario;
	@Column(name = "profesor_id", nullable = false)
	private Long idProfesor;
	@Column(name = "estado")
	private String estado;
	@Column(name = "id_prof_autoriza")
	private Long idProfesorAutoriza;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_aprob")
	private Date fechaAprobacion;
	@OneToMany(mappedBy = "idea", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ArticuloEntity> articulos;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdeaEntity other = (IdeaEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
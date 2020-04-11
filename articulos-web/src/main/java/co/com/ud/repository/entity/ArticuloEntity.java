package co.com.ud.repository.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "id_articulo")
@NamedQueries({
	@NamedQuery(name = "ArticuloEntity.getCountNotificationProf", query = "SELECT count(*) FROM ArticuloEntity art inner join art.idea as ide WHERE art.estado = 'ENVIADO_POR_CORRECCIONES' AND ide.idProfesor = :idProf  "),
	@NamedQuery(name = "ArticuloEntity.getCountNotificationAlum", query = "SELECT count(*) FROM ArticuloEntity art inner join art.idea as ide inner join ide.usuario usu WHERE art.estado = 'RECHAZO_CON_COMENTARIOS' AND usu.id = :idAlum  "),
	
	@NamedQuery(name = "ArticuloEntity.getIdeasNotifiByProf", query = "SELECT art FROM ArticuloEntity art inner join fetch art.idea as ide WHERE art.estado = 'ENVIADO_POR_CORRECCIONES' AND ide.idProfesor = :idProf"),
	@NamedQuery(name = "ArticuloEntity.getIdeasNotifiByAlum", query = "SELECT art FROM ArticuloEntity art inner join fetch art.idea as ide inner join ide.usuario usu WHERE art.estado = 'RECHAZO_CON_COMENTARIOS' AND usu.id = :idAlum "),
	@NamedQuery(name = "ArticuloEntity.getArticulosByIdea", query = "SELECT art FROM ArticuloEntity art inner join fetch art.idea as ide WHERE ide.id = :idIdea "),
	
	@NamedQuery(name = "ArticuloEntity.updateEstado", query = "update ArticuloEntity art set art.estado = :estado WHERE id = :id"),
	@NamedQuery(name = "ArticuloEntity.getAllArticulosByIdIdea", query = "select art from ArticuloEntity art inner join art.idea ide WHERE ide.id = :idIdea"),
	@NamedQuery(name = "ArticuloEntity.getAllArtAprobados", query = "select art from ArticuloEntity art inner join fetch art.idea ide inner join ide.usuario usu WHERE usu.id = :idUsua and art.estado = :estado "),
	
	@NamedQuery(name = "ArticuloEntity.getArticulosByEstado", query = " select art from ArticuloEntity art WHERE art.estado IN ('PUBLICADO', 'ENVIADO_POR_CORRECCIONES', '' ) ")
	
})
@Getter @Setter
public class ArticuloEntity extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articulo_generator")
	@SequenceGenerator(name = "articulo_generator", sequenceName = "articulo_seq", allocationSize = 1)
	private Long id;
	@Column(name = "resumen_ingles")
	private String resumenIngles;
	@Column(name = "resumen_espanol")
	private String resumenEspanol;
	@Column(name = "contenido")
	private String contenido;
	@Column(name = "estado")
	private String estado;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idea_id", nullable = false)
	private IdeaEntity idea;
	@OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComentarioEntity> comentarios = new ArrayList<ComentarioEntity>();
	
	public void addComentario(ComentarioEntity comentario) {
		comentarios.add(comentario);
		comentario.setArticulo(this);
	}
	
	public void removeComentario(ComentarioEntity comentario) {
		comentarios.remove(comentario);
		comentario.setArticulo(null);
	}

}

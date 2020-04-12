package co.com.ud.persistence.entity;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "id_comentario")
@NamedQueries({
	@NamedQuery(name = "ComentarioEntity.getListByIdeaId", query = "select com from ComentarioEntity com inner join fetch com.articulo  arti inner join arti.idea ide where ide.id = :ideaId  ")
})
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class ComentarioEntity extends Auditable<String> {
	
	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comentario_generator")
	@SequenceGenerator(name = "comentario_generator", sequenceName = "comentario_seq", allocationSize = 1)
	private Long id;
	@Column(name = "comentario")
	private String comentario;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "articulo_id",nullable = false)
	private ArticuloEntity articulo;
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
		ComentarioEntity other = (ComentarioEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}

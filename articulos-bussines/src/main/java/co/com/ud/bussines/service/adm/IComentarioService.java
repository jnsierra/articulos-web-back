package co.com.ud.bussines.service.adm;

import java.util.List;

import co.com.ud.persistence.entity.ComentarioEntity;

public interface IComentarioService {
	/**
	 * Metodo con el cual obtiene la lista de comentarios por medio del id del
	 * articulo
	 * 
	 * @param idArticulo
	 * @return
	 */
	List<ComentarioEntity> getComentariosByArticuloId(Long idArticulo);

	/**
	 * Metodo con el cual persisto un comentario
	 * 
	 * @param comentario
	 * @return
	 */
	ComentarioEntity guardarComentario(ComentarioEntity comentario);

	/**
	 * Metodo con el cual obtiene la lista de comentarios de un articulo
	 * 
	 * @param idArt
	 * @return
	 */
	List<ComentarioEntity> obtenerComentariosByArticulo(Long idIdea);
}

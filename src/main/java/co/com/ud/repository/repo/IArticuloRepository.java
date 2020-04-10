package co.com.ud.repository.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ud.repository.entity.ArticuloEntity;

@Repository
public interface IArticuloRepository extends CrudRepository<ArticuloEntity, Long>, JpaRepository<ArticuloEntity, Long> {
	/**
	 * Metodo con el cual cuento las notificaciones que tiene cada profesor
	 * @param idProf
	 * @return
	 */
	Integer getCountNotificationProf(@Param("idProf") Long idProf);
	/**
	 * Metodo con el cual cuento las notificaciones que se tiene cada alumno
	 * @param idAlum
	 * @return
	 */
	Integer getCountNotificationAlum(@Param("idAlum") Long idAlum);
	/**
	 * Metodo con el cual obtengo las ideas que tiene asignado un profesor
	 * @param idProf
	 * @return
	 */
	List<ArticuloEntity> getIdeasNotifiByProf(@Param("idProf")Long idProf);
	/**
	 * Metodo con el cual obtengo las ideas que tiene asignado a un alumno
	 * @param idAlum
	 * @return
	 */
	List<ArticuloEntity> getIdeasNotifiByAlum(@Param("idAlum")Long idAlum);
	/**
	 * Metodo con el cual actualizo el estado de un articulo
	 * @param id
	 * @param estado
	 * @return
	 */
	@Modifying
	@Transactional
	Integer updateEstado(@Param("id") Long id, @Param("estado") String estado);
	/**
	 * Metodo con el cual obtengo todos los articulos por medio de el id de la idea
	 * @param idIdea
	 * @return
	 */
	List<ArticuloEntity> getAllArticulosByIdIdea(@Param("idIdea")Long idIdea);
	/**
	 * Metodo con el cual consulto todos los articulos por el id de la idea
	 * @param idIdea
	 * @return
	 */
	List<ArticuloEntity> getArticulosByIdea(@Param("idIdea")Long idIdea);
	/**
	 * Metodo con el cual obtengo todos los articulos aprobados de un usuario
	 * @param idUsua
	 * @return
	 */
	List<ArticuloEntity> getAllArtAprobados(@Param("idUsua")Long idUsua, @Param("estado")String estado);
	/**
	 * Metodo con el cual obtengo los articulos por estado
	 * @return
	 */
	List<ArticuloEntity> getArticulosByEstado();
}
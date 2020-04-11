package co.com.ud.persistence.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ud.persistence.entity.IdeaEntity;

@Repository
public interface IIdeaRepository extends CrudRepository<IdeaEntity, Long>, JpaRepository<IdeaEntity, Long> {
	/**
	 * Metodo con el cual se busca las ideas de un usuario
	 * 
	 * @param idUsuario
	 * @return
	 */
	List<IdeaEntity> buscarIdeasPorUsuario(@Param("idUsuario") Long idUsuario);

	/**
	 * Metodo con el cual se buscan las ideas que tiene por revisar un profesor por
	 * medio de su estado
	 * 
	 * @param idProfesor
	 * @param estado
	 * @return
	 */
	List<IdeaEntity> buscarIdeaByProfesorAndEstado(@Param("idProfesor")Long idProfesor,@Param("estado") String estado);
	/**
	 * Metodo con el cual se buscan las ideas que tiene por revisar un profesor por
	 * medio de su estado
	 * 
	 * @param idProfesor
	 * @param estado
	 * @return
	 */
	List<IdeaEntity> buscarIdeaByProfesor(@Param("idProfesor")Long idProfesor);
	
	/**
	 * Metodo con el cual se actualiza el estado de una idea
	 * @param idIdea
	 * @param estado
	 * @return
	 */
	@Modifying
	@Transactional
	Integer updateEstado(@Param("idIdea")Long idIdea,@Param("estado") String estado, @Param("idProfAut")Long idProfAut);
	/**
	 * Metodo con el cual se actualiza el estado de la idea a EN_ESPERA
	 * @param idIdea
	 * @return
	 */
	@Modifying
	@Transactional
	Integer updateEstadoEspera(@Param("idIdea")Long idIdea);
	/**
	 * Metodo con el cual obtengo las ideas por medio de su estado
	 * @return
	 */
	List<IdeaEntity> getEstadoIdeas();
}

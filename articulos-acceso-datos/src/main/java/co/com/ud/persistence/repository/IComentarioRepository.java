package co.com.ud.persistence.repository;

import java.util.List;

import co.com.ud.persistence.entity.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IComentarioRepository extends JpaRepository<ComentarioEntity, Long>, CrudRepository<ComentarioEntity, Long> {

	List<ComentarioEntity> getListByIdeaId(@Param("ideaId")Long idIdea);
}

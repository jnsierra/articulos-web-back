package co.com.ud.repository.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ud.repository.entity.ComentarioEntity;

@Repository
public interface IComentarioRepository extends JpaRepository<ComentarioEntity, Long>, CrudRepository<ComentarioEntity, Long> {

	List<ComentarioEntity> getListByIdeaId(@Param("ideaId")Long idIdea);
}

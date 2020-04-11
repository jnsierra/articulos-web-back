package co.com.ud.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ud.persistence.entity.UsuarioEntity;
import co.com.ud.persistence.entity.enumeracion.TipoUsuario;

@Repository
public interface IUsuarioRepository extends CrudRepository<UsuarioEntity, Long>, JpaRepository<UsuarioEntity, Long> {
	/**
	 * Metodo con el cual busco un usuario por medio de su usario y contrasena
	 * 
	 * @param correo
	 * @param contrasena
	 * @return
	 */
	Optional<UsuarioEntity> findByCorreoAndContrasenaAllIgnoreCase(String correo, String contrasena);
	/**
	 * Metodo con el cual obtengo un usuario por medio de su correo
	 * @param correo
	 * @return
	 */
	List<UsuarioEntity> findByCorreoAllIgnoreCase(String correo);
	
	/**
	 * Metodo con el cual obtengo un usuario por medio de su correo
	 * @param correo
	 * @return
	 */
	List<UsuarioEntity> findByTipoUsuario(TipoUsuario tipoUsuario);
	/**
	 * Metodo con el cual se actualiza el tipo de usuario
	 * @param tipoUsuario
	 * @return
	 */
	@Modifying
	@Transactional
	Integer updateTipoUsuario(@Param("tipoUsuario") TipoUsuario tipoUsuario, @Param("id") Long id);

}

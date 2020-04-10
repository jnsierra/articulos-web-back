package co.com.ud.service.adm;

import java.util.List;
import java.util.Optional;

import co.com.ud.adm.dto.TokenDto;
import co.com.ud.repository.entity.UsuarioEntity;
import co.com.ud.repository.entity.enumeracion.TipoUsuario;

public interface IUsuarioService {
	/**
	 * Metodo con el cual realizo el login por medio de un usuario y contrasena
	 * 
	 * @param user
	 * @param pass
	 * @return 
	 */
	Optional<UsuarioEntity> loginUser(String user, String pass);
	
	/**
	 * Metodo con el cual genero el token para las demas solicitudes
	 * @param usuario
	 * @return
	 */
	Optional<TokenDto> generaToken(UsuarioEntity usuario);
	/**
	 * Metodo con el cual obtengo un usuario por medio de su correo
	 * @param correo
	 * @return
	 */
	List<UsuarioEntity> getByCorreo(String correo);
	
	/**
	 * Metodo con el cual obtengo un usuario por medio de su correo
	 * @param correo
	 * @return
	 */
	List<UsuarioEntity> getByTipoUsuario(TipoUsuario tipoUsuario);
	/**
	 * Metodo con el cual obtengo el usuario por medio de su id
	 * @param correo
	 * @return
	 */
	Optional<UsuarioEntity> getById(Long id);
	
	/**
	 * Metodo con el cual inserto un usuario
	 * @param usuario
	 * @return
	 */
	UsuarioEntity insertarUsuario(UsuarioEntity usuario);
	/**
	 * Metodo con el cual obtengo todos los usuarios
	 * @return
	 */
	List<UsuarioEntity> getAllUsers();
	/**
	 * Metodo con el cual actualizo el 
	 * @param tipoUsuario
	 * @param id
	 * @return
	 */
	Boolean updateTipoUsuario(TipoUsuario tipoUsuario, Long id);

}

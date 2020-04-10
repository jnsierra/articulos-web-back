package co.com.ud.service.adm.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import co.com.ud.adm.dto.TokenDto;
import co.com.ud.repository.entity.UsuarioEntity;
import co.com.ud.repository.entity.enumeracion.TipoUsuario;
import co.com.ud.repository.repo.IUsuarioRepository;
import co.com.ud.service.adm.ITokenService;
import co.com.ud.service.adm.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService{
	@Autowired
	IUsuarioRepository usuarioRepository;
	
	@Autowired
	ITokenService tokenService;

	@Override
	public Optional<UsuarioEntity> loginUser(String correo, String contra) {
		return usuarioRepository.findByCorreoAndContrasenaAllIgnoreCase(correo, contra);
	}

	@Override
	public Optional<TokenDto> generaToken(UsuarioEntity usuario) {
		// Busco los roles del usuario
		String roleStr = "ROLE_ADMIN";
		
		Assert.hasLength(roleStr,"El usuario no tiene roles asignados");
		//Genero la lista de roles del usuario
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roleStr);
		//Genero el token
		String jwt = tokenService.generateToken(usuario.getCorreo(), grantedAuthorities);
		
		return Optional.of(TokenDto.of(jwt, TokenService.seconds));
	}

	@Override
	public List<UsuarioEntity> getByCorreo(String correo) {
		return usuarioRepository.findByCorreoAllIgnoreCase(correo);
	}

	@Override
	public UsuarioEntity insertarUsuario(UsuarioEntity usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public List<UsuarioEntity> getAllUsers() {
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<UsuarioEntity> getById(Long id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Boolean updateTipoUsuario(TipoUsuario tipoUsuario, Long id) { 
		return (usuarioRepository.updateTipoUsuario(tipoUsuario, id) >= 1 ) ? Boolean.TRUE: Boolean.FALSE;
	}

	@Override
	public List<UsuarioEntity> getByTipoUsuario(TipoUsuario tipoUsuario) {
		return usuarioRepository.findByTipoUsuario(tipoUsuario);
	}

}

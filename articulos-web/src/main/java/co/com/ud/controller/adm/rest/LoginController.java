package co.com.ud.controller.adm.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.ud.bussines.service.adm.IUsuarioService;
import co.com.ud.bussines.service.adm.dto.TokenDto;
import co.com.ud.controller.adm.dto.UsuarioDto;
import co.com.ud.persistence.entity.UsuarioEntity;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
    IUsuarioService usuarioService;

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> test() {
		return new ResponseEntity<String>("I´m alive", HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenDto> login(@RequestBody UsuarioDto usuarioDto) {
		Optional<UsuarioEntity> usuarioEntity = usuarioService.loginUser(usuarioDto.getCorreo(), usuarioDto.getContrasena());
		if(!usuarioEntity.isPresent()) {
			return new ResponseEntity<>(new TokenDto("Error de usuario y contraseña"),HttpStatus.UNAUTHORIZED);
		}
		Optional<TokenDto> token = usuarioService.generaToken(usuarioEntity.get());
		if(!token.isPresent()) {
			return new ResponseEntity<>(new TokenDto("Error al generar el token"),HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<TokenDto>(token.get(), HttpStatus.OK);
	}
}

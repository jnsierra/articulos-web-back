package co.com.ud.controller.adm.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.com.ud.controller.adm.dto.UsuarioDto;
import co.com.ud.persistence.entity.UsuarioEntity;
import co.com.ud.persistence.entity.enumeracion.TipoUsuario;
import co.com.ud.bussines.service.adm.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v.1/usuarios")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
    IUsuarioService usuarioService;
	@Autowired
	ModelMapper map;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/by/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDto[]> getUserByUser(@RequestParam(name = "correo", required = false) String correo,
                                                      @RequestParam(name = "tipoUsuario", required = false) TipoUsuario tipoUsuario) {
		List<UsuarioEntity> usuarios = new ArrayList<>();
		//Consulta por medio de su correo
		if(tipoUsuario == null) {
			usuarios = usuarioService.getByCorreo(correo);
		}
		//Consulta por medio de su tipo de usuario
		if( tipoUsuario != null) {
			usuarios = usuarioService.getByTipoUsuario(tipoUsuario);
		}
		if (usuarios.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(map.map(usuarios, UsuarioDto[].class), HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDto> insert(@RequestBody(required = true) UsuarioDto usuarioDto) {
		return new ResponseEntity<>(
				map.map(usuarioService.insertarUsuario(map.map(usuarioDto, UsuarioEntity.class)), UsuarioDto.class),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDto[]> getAll() {
		return new ResponseEntity<>(map.map(usuarioService.getAllUsers(), UsuarioDto[].class), HttpStatus.OK);
	}

	@RequestMapping(value = "/{idUsuario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDto> getByUser(@PathVariable("idUsuario") Long idUser) {
		Optional<UsuarioEntity> usuario = usuarioService.getById(idUser);
		if (!usuario.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(map.map(usuario.get(), UsuarioDto.class), HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> updateTipoUsuario(@RequestBody(required = true) UsuarioDto usuarioDto) {
		return new ResponseEntity<>(usuarioService.updateTipoUsuario(usuarioDto.getTipoUsuario(), usuarioDto.getId()),
				HttpStatus.OK);
	}

}

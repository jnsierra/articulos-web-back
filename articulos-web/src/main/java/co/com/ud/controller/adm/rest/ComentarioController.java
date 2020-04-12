package co.com.ud.controller.adm.rest;

import java.util.List;

import co.com.ud.controller.adm.dto.ComentarioDto;
import co.com.ud.persistence.entity.ComentarioEntity;
import co.com.ud.bussines.service.adm.IArticuloService;
import co.com.ud.bussines.service.adm.IComentarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v.1/comentario")
@CrossOrigin(origins = "*")
public class ComentarioController {
	
	@Autowired
    IComentarioService comentarioService;
	
	@Autowired
    IArticuloService articuloService;
	
	@Autowired
	ModelMapper mapper;
	
	@RequestMapping(value = "/rechazo/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ComentarioDto> rechazarConComentario(@RequestBody(required = true) ComentarioDto comentario){
		//Guardo el comentario del articulo
		ComentarioEntity comentarioGuard = comentarioService.guardarComentario( mapper.map(comentario, ComentarioEntity.class) );
		//Cambio el estado del articulo
		articuloService.updateEstadoById(comentario.getArticuloId(), "RECHAZO_CON_COMENTARIOS");
		return new ResponseEntity<>(mapper.map(comentarioGuard, ComentarioDto.class), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/articulo/idea/{idIdea}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ComentarioDto[]> getComentariosByIdArticulo(@PathVariable(name = "idIdea", required = true)Long idIdea){
		List<ComentarioEntity> listComentarios = comentarioService.obtenerComentariosByArticulo(idIdea);
		if(listComentarios.isEmpty()) {
			return new ResponseEntity<>( HttpStatus.NO_CONTENT );
		}
		return new ResponseEntity<>( mapper.map(listComentarios, ComentarioDto[].class) , HttpStatus.OK);
	}
}

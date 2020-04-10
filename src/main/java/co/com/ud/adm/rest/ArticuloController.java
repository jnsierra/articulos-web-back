package co.com.ud.adm.rest;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.ud.adm.dto.ArticuloDto;
import co.com.ud.adm.dto.ArticuloViewDto;
import co.com.ud.adm.dto.ProfesorDto;
import co.com.ud.adm.dto.profesor.NotificacionProfDto;
import co.com.ud.repository.entity.ArticuloEntity;
import co.com.ud.repository.entity.UsuarioEntity;
import co.com.ud.service.adm.IArticuloService;
import co.com.ud.service.adm.IIdeaService;
import co.com.ud.service.adm.IUsuarioService;

@RestController
@RequestMapping(value = "/v.1/articulos")
@CrossOrigin(origins = "*")
public class ArticuloController {
	
	@Autowired
	IArticuloService articuloService;
	@Autowired
	IIdeaService ideaService;
	@Autowired
	IUsuarioService usuarioService;
	@Autowired
	ModelMapper mapper;
	
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticuloDto> saveArticulo(@RequestBody(required = true) ArticuloDto articulo){
		//Cambio todos los articulos a HISTORICO
		articuloService.updateHistoricoByIdea(articulo.getIdeaId());
		
		articulo = mapper.map(articuloService.guardarArticulo(mapper.map(articulo, ArticuloEntity.class)), ArticuloDto.class) ;
		if( articulo != null && articulo.getId() != null && articulo.getIdeaId() != null ) {
			ideaService.updateEstadoIdeaEnEspera(articulo.getIdeaId());
		}
		return new ResponseEntity<>(articulo, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/idea/{idIdea}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticuloDto[]> getAllArtByIdIdea(@PathVariable( name = "idIdea" , required = true) Long idIdea){
		List<ArticuloEntity> articulos = articuloService.getAllArtByIdIdea(idIdea);
		if(articulos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(mapper.map(articulos, ArticuloDto[].class), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticuloViewDto> getById(@PathVariable("id")Long id){
		Optional<ArticuloEntity> articulo = articuloService.getById(id);
		ArticuloViewDto articuloDto = null;
		if( !articulo.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			articuloDto = mapper.map( articulo.get(), ArticuloViewDto.class );
			//Realizamos el mapeo de profesor
			Optional<UsuarioEntity> profesor = usuarioService.getById( articuloDto.getIdea().getIdProfesor() );
			Optional<UsuarioEntity> proAut = usuarioService.getById( articuloDto.getIdea().getIdProfesorAutoriza() );
			if (profesor.isPresent()) {
				articuloDto.getIdea().setProfesorAsignado(new ProfesorDto());
				articuloDto.getIdea().getProfesorAsignado().setId(profesor.get().getId());
				articuloDto.getIdea().getProfesorAsignado().setNombre(profesor.get().getNombre());
			}
			if (proAut.isPresent()) {
				articuloDto.getIdea().setProfesorAutoriza(new ProfesorDto());
				articuloDto.getIdea().getProfesorAutoriza().setId(proAut.get().getId());
				articuloDto.getIdea().getProfesorAutoriza().setNombre(proAut.get().getNombre());
			}
		}
		return new ResponseEntity<>( articuloDto , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/profesor/notificaciones/{idProfesor}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getNumRevisionProfesor(@PathVariable("idProfesor") Long idProf){
		Integer contador = articuloService.getCountNotificationProf(idProf);
		return new ResponseEntity<Integer>( contador , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/profesor/notificaciones/titulos/{idProfesor}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NotificacionProfDto>> getNotificationsProf(@PathVariable("idProfesor") Long idProf){
		return new ResponseEntity<>(articuloService.getTitulosNotifProf(idProf), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/alumno/notificaciones/{idAlumno}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getNumRevisionAlumno(@PathVariable("idAlumno") Long idAlum){
		Integer contador = articuloService.getCountNotificationAlum(idAlum);
		return new ResponseEntity<Integer>( contador , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/alumno/notificaciones/titulos/{idAlumno}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NotificacionProfDto>> getNotificationsAlum(@PathVariable("idAlumno") Long idAlum){
		return new ResponseEntity<>(articuloService.getTitulosNotifAlum(idAlum), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/cambiarestado/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> actualizarEstado(@RequestParam("idArticulo")Long idArticulo,
			@RequestParam("estado") String estado){
		return new ResponseEntity<>(articuloService.updateEstadoById(idArticulo, estado),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/alumno/aprobados/{idAlumno}/")
	public ResponseEntity<ArticuloViewDto[]> obtenerArtAprobados(@PathVariable(name = "idAlumno", required = true) Long idAlumno){
		List<ArticuloEntity> listArt = articuloService.getListArtByIdAlumnoAprobados(idAlumno, "APROBADO");
		ArticuloViewDto[] listArticulo = null;
		if(listArt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			listArticulo = mapper.map(listArt, ArticuloViewDto[].class);			
			for(int i = 0 ; i < listArticulo.length ; i++) {
				Optional<UsuarioEntity> profesor = usuarioService.getById( listArticulo[i].getIdea().getIdProfesor() );
				Optional<UsuarioEntity> proAut = usuarioService.getById( listArticulo[i].getIdea().getIdProfesorAutoriza() );
				if (profesor.isPresent()) {
					listArticulo[i].getIdea().setProfesorAsignado(new ProfesorDto());
					listArticulo[i].getIdea().getProfesorAsignado().setId(profesor.get().getId());
					listArticulo[i].getIdea().getProfesorAsignado().setNombre(profesor.get().getNombre());
				}
				if (proAut.isPresent()) {
					listArticulo[i].getIdea().setProfesorAutoriza(new ProfesorDto());
					listArticulo[i].getIdea().getProfesorAutoriza().setId(proAut.get().getId());
					listArticulo[i].getIdea().getProfesorAutoriza().setNombre(proAut.get().getNombre());
				}
			}
		}
		return new ResponseEntity<>( listArticulo , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/alumno/publicados/{idAlumno}/")
	public ResponseEntity<ArticuloViewDto[]> obtenerArtPublicados(@PathVariable(name = "idAlumno", required = true) Long idAlumno){
		List<ArticuloEntity> listArt = articuloService.getListArtByIdAlumnoAprobados(idAlumno, "PUBLICADO");
		ArticuloViewDto[] listArticulo = null;
		if(listArt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			listArticulo = mapper.map(listArt, ArticuloViewDto[].class);			
			for(int i = 0 ; i < listArticulo.length ; i++) {
				Optional<UsuarioEntity> profesor = usuarioService.getById( listArticulo[i].getIdea().getIdProfesor() );
				Optional<UsuarioEntity> proAut = usuarioService.getById( listArticulo[i].getIdea().getIdProfesorAutoriza() );
				if (profesor.isPresent()) {
					listArticulo[i].getIdea().setProfesorAsignado(new ProfesorDto());
					listArticulo[i].getIdea().getProfesorAsignado().setId(profesor.get().getId());
					listArticulo[i].getIdea().getProfesorAsignado().setNombre(profesor.get().getNombre());
				}
				if (proAut.isPresent()) {
					listArticulo[i].getIdea().setProfesorAutoriza(new ProfesorDto());
					listArticulo[i].getIdea().getProfesorAutoriza().setId(proAut.get().getId());
					listArticulo[i].getIdea().getProfesorAutoriza().setNombre(proAut.get().getNombre());
				}
			}
		}
		return new ResponseEntity<>( listArticulo , HttpStatus.OK);
	}

}
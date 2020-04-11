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

import co.com.ud.adm.dto.IdeaDto;
import co.com.ud.adm.dto.IdeaProfesorDto;
import co.com.ud.adm.dto.ProfesorDto;
import co.com.ud.adm.dto.UsuarioDto;
import co.com.ud.adm.dto.alumno.IdeaAlumnoDto;
import co.com.ud.persistence.entity.IdeaEntity;
import co.com.ud.persistence.entity.UsuarioEntity;
import co.com.ud.service.adm.IUsuarioService;
import co.com.ud.service.adm.impl.IdeaService;

@RestController
@RequestMapping("/v.1/ideas")
@CrossOrigin(origins = "*")
public class IdeaController {
	@Autowired
    IdeaService ideaService;
	@Autowired
    IUsuarioService usuarioService;
	@Autowired
	ModelMapper mapper;

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IdeaDto> save(@RequestBody(required = true) IdeaDto idea) {
		return new ResponseEntity<>(
				mapper.map(ideaService.guardarIdea(mapper.map(idea, IdeaEntity.class)), IdeaDto.class),
				HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}/")
	public ResponseEntity<IdeaAlumnoDto> findById(@PathVariable(value = "id", required = true)Long id){
		Optional<IdeaEntity> idea = ideaService.findByIdea(id);
		IdeaAlumnoDto ideaAlumno = null;
		if( !idea.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			ideaAlumno = mapper.map(idea.get(), IdeaAlumnoDto.class);
			
			//Realizamos el mapeo de profesor
			Optional<UsuarioEntity> profesor = usuarioService.getById( ideaAlumno.getIdProfesor() );
			Optional<UsuarioEntity> proAut = usuarioService.getById( ideaAlumno.getIdProfesorAutoriza() );
			if (profesor.isPresent()) {
				ideaAlumno.setProfesorAsignado(new ProfesorDto());
				ideaAlumno.getProfesorAsignado().setId(profesor.get().getId());
				ideaAlumno.getProfesorAsignado().setNombre(profesor.get().getNombre());
			}
			if (proAut.isPresent()) {
				ideaAlumno.setProfesorAutoriza(new ProfesorDto());
				ideaAlumno.getProfesorAutoriza().setId(proAut.get().getId());
				ideaAlumno.getProfesorAutoriza().setNombre(proAut.get().getNombre());
			}
		}
		return new ResponseEntity<>(ideaAlumno, HttpStatus.OK );		
	}

	@RequestMapping(value = "/by/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IdeaDto[]> getIdeasByParams(@RequestParam(name = "idUsuario") Long idUsuario) {
		IdeaDto[] ideas = mapper.map(ideaService.getIdeaByUsuario(idUsuario), IdeaDto[].class);
		if (ideas != null && ideas.length != 0) {
			for (IdeaDto item : ideas) {
				Optional<UsuarioEntity> profesor = usuarioService.getById(item.getIdProfesor());
				if (profesor.isPresent()) {
					item.setProfesores(new ProfesorDto());
					item.getProfesores().setId(profesor.get().getId());
					item.getProfesores().setNombre(profesor.get().getNombre());
				}

			}
		}
		return new ResponseEntity<>(ideas, HttpStatus.OK);
	}

	@RequestMapping(value = "/profesor/{idProfesor}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IdeaProfesorDto[]> getIdeasRevisarProfesor(@PathVariable(name = "idProfesor", required = true) Long idProfesor, @RequestParam(name = "estado", required = false) String estado) {
		List<IdeaEntity> ideas = ideaService.getIdeaByProfesorAndEstado(estado, idProfesor);
		IdeaProfesorDto[] ideasRta = new IdeaProfesorDto[ideas.size()];
		int i = 0;
		for(IdeaEntity item : ideas) {
			IdeaProfesorDto aux = new IdeaProfesorDto();
			aux.setId(item.getId());
			aux.setContenido(item.getContenido());
			aux.setIdProfesor(item.getIdProfesor());
			aux.setEstado(item.getEstado());
			aux.setTitulo(item.getTitulo());
			aux.setAlumno(new UsuarioDto());
			aux.getAlumno().setId(item.getUsuario().getId());
			aux.getAlumno().setNombre(item.getUsuario().getNombre());
			ideasRta[i] = aux;
			i++;
		}
		return new ResponseEntity<>(ideasRta, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/estado/{idIdea}/{estado}/{idProfesor}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> cambiarEstadoIdea(@PathVariable("idIdea") Long idIdea, 
			@PathVariable("estado") String estado, @PathVariable("idProfesor")Long idProfesor){
		return new ResponseEntity<>(ideaService.updateEstadoIdea(idIdea, estado, idProfesor), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{idIdea}/articulo/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> buscaIdArtByIdIdea(@PathVariable("idIdea")Long idIdea){
		Optional<IdeaEntity> idea = ideaService.findByIdea(idIdea);
		if(!idea.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(idea.get().getArticulos().get(0).getId(),HttpStatus.OK);
	}

}

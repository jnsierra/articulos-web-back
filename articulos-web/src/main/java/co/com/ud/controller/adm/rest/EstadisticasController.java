package co.com.ud.controller.adm.rest;

import java.util.Map;

import co.com.ud.bussines.service.adm.impl.ArticuloService;
import co.com.ud.bussines.service.adm.impl.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v.1/estadisticas")
@CrossOrigin(origins = "*")
public class EstadisticasController {

	@Autowired
    IdeaService ideaService;
	
	@Autowired
    ArticuloService articuloService;
	
	@RequestMapping(value = "/ideas/estado/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Long>> obtenerIdeasByEstado(){
		return new ResponseEntity<>(ideaService.getEstadoIdeas(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/articulo/estado/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Long>> obtenerArticuloByEstado(){
		return new ResponseEntity<>(articuloService.getArticulosByEstado(), HttpStatus.OK);
	}
}

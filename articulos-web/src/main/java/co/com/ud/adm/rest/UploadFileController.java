package co.com.ud.adm.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.ud.adm.dto.UploadArticuloDto;
import co.com.ud.service.adm.IArticuloService;
import co.com.ud.service.adm.IUploadService;

@RestController
@RequestMapping("/v.1/upload")
@CrossOrigin(origins = "*")
public class UploadFileController {
	
	@Autowired
	IUploadService uploadService;
	@Autowired
	IArticuloService articuloService;
	
	@RequestMapping(value = "/articulo/" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> uploadFileArt(@RequestBody(required = true) UploadArticuloDto upload){
		//Guardo el pdf en el repositorio
		Boolean uploadFile = uploadService.saveFileRepository(upload.getBase64(), upload.getIdArticulo());
		if(uploadFile) {
			//Cambio el estado del articulo a PUBLICADO
			uploadFile = articuloService.updateEstadoById(upload.getIdArticulo(), "PUBLICADO");
		}
		return new ResponseEntity<Boolean>(uploadFile, HttpStatus.OK);
	}
	
}

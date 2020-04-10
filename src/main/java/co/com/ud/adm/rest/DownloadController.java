package co.com.ud.adm.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.ud.adm.dto.UploadArticuloDto;
import co.com.ud.service.adm.IUploadService;

@RestController
@RequestMapping("/v.1/download")
@CrossOrigin(origins = "*")
public class DownloadController {
	
	@Autowired
	IUploadService uploadService; 

	@RequestMapping(value = "/articulo/{idArticulo}/" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<UploadArticuloDto> downloadImage(@PathVariable( name = "idArticulo" , required = true)Long idArticulo){
		System.out.println(idArticulo);
		UploadArticuloDto articulo = uploadService.pdfToBase64(idArticulo);		
		return new ResponseEntity<>(articulo, HttpStatus.OK);
	}
	
}

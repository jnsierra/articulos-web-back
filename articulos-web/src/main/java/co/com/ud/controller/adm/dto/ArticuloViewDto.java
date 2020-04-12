package co.com.ud.controller.adm.dto;

import co.com.ud.controller.adm.dto.alumno.IdeaAlumnoDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticuloViewDto {
	
	private Long id;
	private String resumenIngles;
	private String resumenEspanol;
	private String contenido;
	private String estado;
	private IdeaAlumnoDto idea;

}

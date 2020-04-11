package co.com.ud.adm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IdeaProfesorDto {
	
	private Long id;
	private String titulo;
	private String contenido;
	private Long idProfesor;
	private String estado;
	private UsuarioDto alumno;

}

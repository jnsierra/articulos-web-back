package co.com.ud.controller.adm.dto.alumno;

import java.util.Date;

import co.com.ud.controller.adm.dto.ProfesorDto;
import co.com.ud.controller.adm.dto.UsuarioDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IdeaAlumnoDto {
	
	private Long id;
	private String titulo;
	private String contenido;
	private UsuarioDto usuario;
	private Long idProfesor;
	private String estado;
	private Long idProfesorAutoriza;
	private Date fechaAprobacion;

	private ProfesorDto profesorAsignado;
	private ProfesorDto profesorAutoriza;
}

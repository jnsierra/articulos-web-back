package co.com.ud.adm.dto;

import co.com.ud.persistence.entity.enumeracion.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioDto {
		
	private Long id;
	private String correo;
	private String contrasena;
	private String nombre;
	private String cambioContra;
	private TipoUsuario tipoUsuario;

}

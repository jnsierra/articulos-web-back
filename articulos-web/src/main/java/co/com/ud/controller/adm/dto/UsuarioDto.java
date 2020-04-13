package co.com.ud.controller.adm.dto;

import co.com.ud.persistence.entity.enumeracion.TipoUsuario;
import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class UsuarioDto {
		
	private Long id;
	private String correo;
	private String contrasena;
	private String nombre;
	private String cambioContra;
	private TipoUsuario tipoUsuario;

}

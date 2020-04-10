package co.com.ud.adm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComentarioDto {
	
	private Long id;
	private String comentario;
	private Long articuloId;

}

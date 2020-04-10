package co.com.ud.adm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticuloDto {
	
	private Long id;
	private String resumenIngles;
	private String resumenEspanol;
	private String contenido;
	private String estado;
	private Long ideaId;

}

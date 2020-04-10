package co.com.ud.adm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenDto {
	
	private String token;
	private Integer time;
	private String mensaje;
	
	
	public static TokenDto of(String token, Integer time) {
		return new TokenDto(token, time);
	}
	
	public static TokenDto of(String mensaje) {
		return new TokenDto(mensaje);
	}


	private TokenDto(String token, Integer time) {
		super();
		this.token = token;
		this.time = time;
	}


	public TokenDto(String mensaje) {
		super();
		this.mensaje = mensaje;
	}
	
	

}

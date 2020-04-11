package co.com.ud.adm.dto.profesor;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NotificacionProfDto {
	
	private Long idArticulo;
	private Long idIdea;
	private String tituloIdea;
	private String tipoUsuario;
	
	public static NotificacionProfDto of(Long id,Long idIdea, String tituloIdea, String tipoUsuario) {
		NotificacionProfDto notificacion = new NotificacionProfDto();
		notificacion.setIdArticulo(id);
		notificacion.setTituloIdea(tituloIdea);
		notificacion.setTipoUsuario(tipoUsuario);
		notificacion.setIdIdea(idIdea);
		return notificacion;
	}

}

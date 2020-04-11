package co.com.ud.service.adm;

import co.com.ud.adm.dto.UploadArticuloDto;

public interface IUploadService {
	/**
	 * Metodo con el cual guardo un base 64 en el repositorio de documento
	 * @param base64
	 * @param idArt
	 * @return
	 */
	Boolean saveFileRepository(String base64, Long idArt);
	/**
	 * Metodo con el cual convierto un pdf en base 64
	 * @param idArt
	 * @return
	 */
	UploadArticuloDto pdfToBase64(Long idArt);

}

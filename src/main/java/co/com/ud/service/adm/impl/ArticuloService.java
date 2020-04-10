package co.com.ud.service.adm.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ud.adm.dto.profesor.NotificacionProfDto;
import co.com.ud.repository.entity.ArticuloEntity;
import co.com.ud.repository.repo.IArticuloRepository;
import co.com.ud.service.adm.IArticuloService;

@Service
public class ArticuloService implements IArticuloService {
	
	@Autowired
	IArticuloRepository articuloRepository;

	@Override
	public ArticuloEntity guardarArticulo(ArticuloEntity articulo) {
		return articuloRepository.save(articulo);
	}

	@Override
	public Integer getCountNotificationProf(Long idProf) {
		return articuloRepository.getCountNotificationProf(idProf);
	}

	@Override
	public List<NotificacionProfDto> getTitulosNotifProf(Long idProf) {
		List<ArticuloEntity> articulos = articuloRepository.getIdeasNotifiByProf(idProf);
		return  articulos.stream().parallel()
				.map(item -> NotificacionProfDto.of(item.getId(), null, item.getIdea().getTitulo(), "PROFESOR") )
				.collect(Collectors.toList());
	}
	
	@Override
	public List<NotificacionProfDto> getTitulosNotifAlum(Long idAlumno) {
		List<ArticuloEntity> articulos = articuloRepository.getIdeasNotifiByAlum(idAlumno);
		return articulos.stream().parallel()
				.map(item -> NotificacionProfDto.of(item.getId(), item.getIdea().getId(),item.getIdea().getTitulo(), "ALUMNO" ))
				.collect(Collectors.toList());
	}
	
	@Override
	public Optional<ArticuloEntity> getById(Long id){
		return articuloRepository.findById(id);
	}

	@Override
	public Boolean updateEstadoById(Long id, String estado) {
		return articuloRepository.updateEstado(id, estado) > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Integer getCountNotificationAlum(Long idAlumno) {
		return articuloRepository.getCountNotificationAlum(idAlumno);
	}

	@Override
	public List<ArticuloEntity> getAllArtByIdIdea(Long IdIdea) {
		return articuloRepository.getAllArticulosByIdIdea(IdIdea);
	}

	@Override
	public Boolean updateHistoricoByIdea(Long idIdea) {
		//Obtengo todos los articulos
		List<ArticuloEntity> listArt = articuloRepository.getArticulosByIdea(idIdea);
		if( listArt.isEmpty() ) {
			return Boolean.TRUE;
		}
		for(ArticuloEntity item : listArt) {
			articuloRepository.updateEstado(item.getId(), "HISTORICO");
		}
		return Boolean.TRUE;
	}

	@Override
	public List<ArticuloEntity> getListArtByIdAlumnoAprobados(Long idAlumn, String estado) {
		return articuloRepository.getAllArtAprobados(idAlumn, estado);
	}

	@Override
	public Map<String, Long> getArticulosByEstado() {
		return articuloRepository.getArticulosByEstado().stream().parallel()
				.collect(Collectors.groupingBy(ArticuloEntity::getEstado, Collectors.counting()));
	}

}

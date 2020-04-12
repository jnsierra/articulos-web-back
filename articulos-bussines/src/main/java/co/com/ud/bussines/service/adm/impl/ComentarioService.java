package co.com.ud.bussines.service.adm.impl;

import java.util.List;

import co.com.ud.persistence.entity.ComentarioEntity;
import co.com.ud.persistence.repository.IComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ud.bussines.service.adm.IComentarioService;

@Service
public class ComentarioService implements IComentarioService {

	private final IComentarioRepository comentarioRepository;

	@Autowired
	public ComentarioService(IComentarioRepository comentarioRepository) {
		this.comentarioRepository = comentarioRepository;
	}

	@Override
	public List<ComentarioEntity> getComentariosByArticuloId(Long idArticulo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComentarioEntity guardarComentario(ComentarioEntity comentario) {
		return comentarioRepository.save(comentario);
	}

	@Override
	public List<ComentarioEntity> obtenerComentariosByArticulo(Long idIdea) {
		return comentarioRepository.getListByIdeaId(idIdea);
	}

}

package co.com.ud.service.adm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ud.repository.entity.ComentarioEntity;
import co.com.ud.repository.repo.IComentarioRepository;
import co.com.ud.service.adm.IComentarioService;

@Service
public class ComentarioService implements IComentarioService {
	
	@Autowired
	IComentarioRepository comentarioRepository;

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

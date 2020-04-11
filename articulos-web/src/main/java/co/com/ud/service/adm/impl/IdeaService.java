package co.com.ud.service.adm.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import co.com.ud.repository.entity.IdeaEntity;
import co.com.ud.repository.entity.UsuarioEntity;
import co.com.ud.repository.repo.IIdeaRepository;
import co.com.ud.service.adm.IIdeaService;
import co.com.ud.service.adm.IUsuarioService;

@Service
public class IdeaService implements IIdeaService {

	@Autowired
	IIdeaRepository ideaRepository;
	@Autowired
	IUsuarioService usuarioService;

	private static final Logger logger = LoggerFactory.getLogger(IdeaService.class);

	@Override
	public IdeaEntity guardarIdea(IdeaEntity idea) {
		String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Usuario Loggeado: ".concat(usuario));
		// Buscamos el id del usuario logeado
		List<UsuarioEntity> usuariosId = usuarioService.getByCorreo(usuario);
		Assert.notEmpty(usuariosId, "Error al buscar el id del usuario logeado");
		logger.info("Este es el id del usuario loggeado: " + usuariosId.get(0).getId());
		idea.setUsuario(usuariosId.get(0));
		return ideaRepository.save(idea);
	}

	public Optional<IdeaEntity> findByIdea(Long id) {
		return ideaRepository.findById(id);
	}

	@Override
	public List<IdeaEntity> getIdeaByUsuario(Long idUsuario) {
		return ideaRepository.buscarIdeasPorUsuario(idUsuario);
	}

	@Override
	public List<IdeaEntity> getIdeaByProfesorAndEstado(String estado, Long idProfesor) {
		if (estado == null) {
			return ideaRepository.buscarIdeaByProfesor(idProfesor);
		}
		return ideaRepository.buscarIdeaByProfesorAndEstado(idProfesor, estado);
	}

	@Override
	public Boolean updateEstadoIdea(Long idIdea, String estado, Long idProfAut) {
		Integer objUpdated = ideaRepository.updateEstado(idIdea, estado, idProfAut);
		return (objUpdated >= 1) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean updateEstadoIdeaEnEspera(Long idIdea) {
		Integer objUpdated = ideaRepository.updateEstadoEspera(idIdea);
		return (objUpdated >= 1) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Map<String, Long> getEstadoIdeas() {
		return ideaRepository.getEstadoIdeas().stream().parallel()
				.collect(Collectors.groupingBy(IdeaEntity::getEstado, Collectors.counting()));
	}

}

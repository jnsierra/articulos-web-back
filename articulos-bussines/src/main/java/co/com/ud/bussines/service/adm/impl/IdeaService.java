package co.com.ud.bussines.service.adm.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import co.com.ud.persistence.repository.IIdeaRepository;
import co.com.ud.bussines.service.auth.IAuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import co.com.ud.persistence.entity.IdeaEntity;
import co.com.ud.persistence.entity.UsuarioEntity;
import co.com.ud.bussines.service.adm.IIdeaService;
import co.com.ud.bussines.service.adm.IUsuarioService;

@Service
public class IdeaService implements IIdeaService {


	private final IIdeaRepository ideaRepository;
	private final IUsuarioService usuarioService;
	private final IAuthenticationFacade authenticationFacade;
	private static final Logger logger = LoggerFactory.getLogger(IdeaService.class);

	@Autowired
	public IdeaService(IIdeaRepository ideaRepository, IUsuarioService usuarioService, IAuthenticationFacade authenticationFacade) {
		this.ideaRepository = ideaRepository;
		this.usuarioService = usuarioService;
		this.authenticationFacade = authenticationFacade;
	}

	@Override
	public IdeaEntity guardarIdea(IdeaEntity idea) {
		String usuario = authenticationFacade.getAuthName();
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

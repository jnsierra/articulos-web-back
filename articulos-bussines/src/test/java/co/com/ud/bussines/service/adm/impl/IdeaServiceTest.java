package co.com.ud.bussines.service.adm.impl;

import co.com.ud.persistence.repository.IIdeaRepository;
import co.com.ud.persistence.entity.IdeaEntity;
import co.com.ud.persistence.entity.UsuarioEntity;
import co.com.ud.bussines.service.adm.IIdeaService;
import co.com.ud.bussines.service.adm.IUsuarioService;
import co.com.ud.bussines.service.auth.IAuthenticationFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class IdeaServiceTest {

    private IIdeaService ideaService;
    @Mock
    private IIdeaRepository ideaRepository;
    @Mock
    private IUsuarioService usuarioService;
    @Mock
    private IAuthenticationFacade authenticationFacade;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.ideaService = new IdeaService(ideaRepository, usuarioService, authenticationFacade);
    }

    @Test
    public void testGuardarIdea(){

        IdeaEntity ideaInput = IdeaEntity.builder()
                .articulos(new ArrayList<>())
                .contenido("Es una idea")
                .estado("ACTIVO")
                .fechaAprobacion(new Date())
                .idProfesor(Long.valueOf(1))
                .idProfesorAutoriza(Long.valueOf(1))
                .titulo("Este es el titulo")
                .usuario(new UsuarioEntity())
                .build();
        IdeaEntity ideaOutput = IdeaEntity.builder()
                .id(Long.valueOf(1))
                .articulos(new ArrayList<>())
                .contenido("Es una idea")
                .estado("ACTIVO")
                .fechaAprobacion(new Date())
                .idProfesor(Long.valueOf(1))
                .idProfesorAutoriza(Long.valueOf(1))
                .titulo("Este es el titulo")
                .usuario(new UsuarioEntity())
                .build();
        UsuarioEntity usuario = UsuarioEntity.builder()
                .id(Long.valueOf(1))
                .correo("jnsierrac@gmail.com")
                .nombre("jesus nicolas")
                .build();
        List<UsuarioEntity> listUsua = new ArrayList<>();
        listUsua.add(usuario);
        Mockito.when(usuarioService.getByCorreo("jnsierrac@gmail.com")).thenReturn(listUsua);
        Mockito.when(authenticationFacade.getAuthName()).thenReturn("jnsierrac@gmail.com");
        Mockito.when(ideaRepository.save(ideaInput)).thenReturn(ideaOutput);

        IdeaEntity rta = ideaService.guardarIdea(ideaInput);
        Assert.assertNotNull(rta);
    }

    @Test
    public void testUpdateEstadoIdeaSUCCESS(){
        Long idIdea = Long.valueOf("1");
        String estado = "ACTIVO";
        Long idProfAut = Long.valueOf(1);
        Mockito.when(ideaRepository.updateEstado(idIdea, estado, idProfAut)).thenReturn(Integer.valueOf(1));

        Boolean valida = ideaService.updateEstadoIdea(idIdea, estado, idProfAut);
        Assert.assertEquals(Boolean.TRUE, valida);
    }

    @Test
    public void testUpdateEstadoIdeaFAILED(){
        Long idIdea = Long.valueOf("1");
        String estado = "ACTIVO";
        Long idProfAut = Long.valueOf(1);
        Mockito.when(ideaRepository.updateEstado(idIdea, estado, idProfAut)).thenReturn(Integer.valueOf(0));

        Boolean valida = ideaService.updateEstadoIdea(idIdea, estado, idProfAut);
        Assert.assertEquals(Boolean.FALSE, valida);
    }

    @Test
    public void testGetIdeaByProfesorAndEstadoSUCCESS(){
        Long idProfesor = Long.valueOf(1);
        String estado = "ACTIVO";
        IdeaEntity ideaInput = IdeaEntity.builder()
                .articulos(new ArrayList<>())
                .contenido("Es una idea")
                .estado("ACTIVO")
                .fechaAprobacion(new Date())
                .idProfesor(Long.valueOf(1))
                .idProfesorAutoriza(Long.valueOf(1))
                .titulo("Este es el titulo")
                .usuario(new UsuarioEntity())
                .build();
        ArrayList<IdeaEntity> listaId = new ArrayList<>();
        listaId.add(ideaInput);
        Mockito.when(ideaRepository.buscarIdeaByProfesorAndEstado(idProfesor, estado)).thenReturn(listaId);

        List<IdeaEntity> listIdea = ideaService.getIdeaByProfesorAndEstado(estado, idProfesor);

        Assert.assertNotNull(listIdea);
    }

    @Test
    public void testGetIdeaByProfesorAndEstadoFAILED(){
        Long idProfesor = Long.valueOf(1);
        String estado = "ACTIVO";
        IdeaEntity ideaInput = IdeaEntity.builder()
                .articulos(new ArrayList<>())
                .contenido("Es una idea")
                .estado("ACTIVO")
                .fechaAprobacion(new Date())
                .idProfesor(Long.valueOf(1))
                .idProfesorAutoriza(Long.valueOf(1))
                .titulo("Este es el titulo")
                .usuario(new UsuarioEntity())
                .build();
        ArrayList<IdeaEntity> listaId = new ArrayList<>();
        listaId.add(ideaInput);
        Mockito.when(ideaRepository.buscarIdeaByProfesor(idProfesor)).thenReturn(listaId);

        List<IdeaEntity> listIdea = ideaService.getIdeaByProfesorAndEstado(null, idProfesor);

        Assert.assertNotNull(listIdea);
    }

    @Test
    public void testUpdateEstadoIdeaEnEsperaSUCCESS(){
        Long idIdea = Long.valueOf(1);
        Mockito.when(ideaRepository.updateEstadoEspera(idIdea)).thenReturn(1);
        Boolean rta = ideaService.updateEstadoIdeaEnEspera(idIdea);
        Assert.assertNotNull(rta);
    }

    @Test
    public void testUpdateEstadoIdeaEnEsperaFAILED(){
        Long idIdea = Long.valueOf(1);
        Mockito.when(ideaRepository.updateEstadoEspera(idIdea)).thenReturn(0);
        Boolean rta = ideaService.updateEstadoIdeaEnEspera(idIdea);
        Assert.assertNotNull(rta);
    }

}
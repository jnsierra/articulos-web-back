package co.com.ud.service.adm.impl;

import co.com.ud.adm.dto.profesor.NotificacionProfDto;
import co.com.ud.persistence.entity.ArticuloEntity;
import co.com.ud.persistence.entity.IdeaEntity;
import co.com.ud.persistence.entity.enumeracion.ESTADO_IDEA;
import co.com.ud.persistence.repository.IArticuloRepository;
import co.com.ud.service.adm.IArticuloService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ArticuloServiceTest {

    private IArticuloService articuloService;
    @Mock
    private IArticuloRepository articuloRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.articuloService = new ArticuloService(articuloRepository);
    }
    @Test
    public void testGuardarArticulo(){
        ArticuloEntity artInput = ArticuloEntity.builder()
                .comentarios(new ArrayList<>())
                .contenido("Esto es el contenido de la idea")
                .estado(ESTADO_IDEA.CREADA)
                .idea(IdeaEntity.builder().build())
                .resumenEspanol("Este es el cuerpo de la idea")
                .resumenIngles("English Idea")
                .build();
        ArticuloEntity artOutput = ArticuloEntity.builder()
                .comentarios(new ArrayList<>())
                .id(Long.valueOf(1))
                .contenido("Esto es el contenido de la idea")
                .estado(ESTADO_IDEA.CREADA)
                .idea(IdeaEntity.builder().build())
                .resumenEspanol("Este es el cuerpo de la idea")
                .resumenIngles("English Idea")
                .build();
        Mockito.when(articuloRepository.save(artInput)).thenReturn(artOutput);
        ArticuloEntity rest =  this.articuloService.guardarArticulo(artInput);
        Assert.assertNotNull(rest);
    }

    @Test
    public void testGetCountNotificationProf(){
        Long idProf = Long.valueOf("1");
        Mockito.when(articuloRepository.getCountNotificationProf(idProf)).thenReturn(Integer.valueOf(2));
        Integer rta = articuloService.getCountNotificationProf(idProf);
        Assert.assertNotNull(idProf);
    }

    @Test
    public void testGetTitulosNotifProf(){
        Long idProf = Long.valueOf("1");
        ArticuloEntity artUno = ArticuloEntity
                .builder()
                .id(Long.valueOf(1))
                .comentarios(new ArrayList<>())
                .contenido("Idea Uno")
                .estado(ESTADO_IDEA.CREADA)
                .idea(new IdeaEntity())
                .resumenEspanol("Contenido Idea Uno")
                .resumenIngles("Abstract in English")
                .build();
        ArticuloEntity artDos = ArticuloEntity
                .builder()
                .id(Long.valueOf(1))
                .comentarios(new ArrayList<>())
                .contenido("Idea Dos")
                .estado(ESTADO_IDEA.CREADA)
                .idea(new IdeaEntity())
                .resumenEspanol("Contenido Idea Dos")
                .resumenIngles("Abstract in English")
                .build();
        List<ArticuloEntity> listRta = new  ArrayList<>();
        listRta.add(artUno);
        listRta.add(artDos);

        Mockito.when(articuloRepository.getIdeasNotifiByProf(idProf)).thenReturn(listRta);
        List<NotificacionProfDto> rta = articuloService.getTitulosNotifProf(idProf);
        Assert.assertNotNull(rta);
        Assert.assertFalse(rta.isEmpty());
    }

    @Test
    public void testGetArticulosByEstado(){
        ArticuloEntity artUno = ArticuloEntity
                .builder()
                .id(Long.valueOf(1))
                .comentarios(new ArrayList<>())
                .contenido("Idea Uno")
                .estado(ESTADO_IDEA.CREADA)
                .idea(new IdeaEntity())
                .resumenEspanol("Contenido Idea Uno")
                .resumenIngles("Abstract in English")
                .build();
        ArticuloEntity artDos = ArticuloEntity
                .builder()
                .id(Long.valueOf(1))
                .comentarios(new ArrayList<>())
                .contenido("Idea Dos")
                .estado(ESTADO_IDEA.CREADA)
                .idea(new IdeaEntity())
                .resumenEspanol("Contenido Idea Dos")
                .resumenIngles("Abstract in English")
                .build();
        List<ArticuloEntity> listRta = new  ArrayList<>();
        listRta.add(artUno);
        listRta.add(artDos);
        Mockito.when(articuloRepository.getArticulosByEstado()).thenReturn(listRta);

        Map<String, Long> rta = articuloService.getArticulosByEstado();
        Assert.assertNotNull(rta);

    }
    @Test
    public void testUpdateHistoricoByIdeaSUCCESS(){
        Long idIdea = Long.valueOf(1);
        ArticuloEntity artUno = ArticuloEntity
                .builder()
                .id(Long.valueOf(1))
                .comentarios(new ArrayList<>())
                .contenido("Idea Uno")
                .estado(ESTADO_IDEA.CREADA)
                .idea(new IdeaEntity())
                .resumenEspanol("Contenido Idea Uno")
                .resumenIngles("Abstract in English")
                .build();
        ArticuloEntity artDos = ArticuloEntity
                .builder()
                .id(Long.valueOf(1))
                .comentarios(new ArrayList<>())
                .contenido("Idea Dos")
                .estado(ESTADO_IDEA.CREADA)
                .idea(new IdeaEntity())
                .resumenEspanol("Contenido Idea Dos")
                .resumenIngles("Abstract in English")
                .build();
        List<ArticuloEntity> listRta = new  ArrayList<>();
        listRta.add(artUno);
        listRta.add(artDos);
        Mockito.when(articuloRepository.getArticulosByIdea(idIdea)).thenReturn(listRta);
        Boolean rta = articuloService.updateHistoricoByIdea(idIdea);
        Assert.assertTrue(rta);
    }

    @Test
    public void testUpdateHistoricoByIdeaEMPTY(){
        Long idIdea = Long.valueOf(1);
        List<ArticuloEntity> listRta = new  ArrayList<>();
        Mockito.when(articuloRepository.getArticulosByIdea(idIdea)).thenReturn(listRta);
        Boolean rta = articuloService.updateHistoricoByIdea(idIdea);
        Assert.assertTrue(rta);
    }

    @Test
    public void testGetTitulosNotifAlum(){
        Long idAlumno = Long.valueOf(1);
        ArticuloEntity artUno = ArticuloEntity
                .builder()
                .id(Long.valueOf(1))
                .comentarios(new ArrayList<>())
                .contenido("Idea Uno")
                .estado(ESTADO_IDEA.CREADA)
                .idea(new IdeaEntity())
                .resumenEspanol("Contenido Idea Uno")
                .resumenIngles("Abstract in English")
                .build();
        ArticuloEntity artDos = ArticuloEntity
                .builder()
                .id(Long.valueOf(1))
                .comentarios(new ArrayList<>())
                .contenido("Idea Dos")
                .estado(ESTADO_IDEA.CREADA)
                .idea(new IdeaEntity())
                .resumenEspanol("Contenido Idea Dos")
                .resumenIngles("Abstract in English")
                .build();
        List<ArticuloEntity> listRta = new  ArrayList<>();
        listRta.add(artUno);
        listRta.add(artDos);
        Mockito.when(articuloRepository.getIdeasNotifiByAlum(idAlumno)).thenReturn(listRta);

        List<NotificacionProfDto> rta = articuloService.getTitulosNotifAlum(idAlumno);

        Assert.assertNotNull(rta);
    }

}
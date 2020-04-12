package co.com.ud.service.adm.impl;

import co.com.ud.persistence.entity.ArticuloEntity;
import co.com.ud.persistence.entity.ComentarioEntity;
import co.com.ud.persistence.repository.IComentarioRepository;
import co.com.ud.service.adm.IComentarioService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ComentarioServiceTest {

    private IComentarioService comentarioService;
    @Mock
    private IComentarioRepository comentarioRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        comentarioService = new ComentarioService(comentarioRepository);
    }

    @Test
    public void testGuardarComentario(){
        ComentarioEntity comInput = ComentarioEntity.builder()
                .articulo(new ArticuloEntity())
                .comentario("Este es un comentario")
                .build();
        ComentarioEntity comOutput = ComentarioEntity.builder()
                .id(Long.valueOf("1"))
                .articulo(new ArticuloEntity())
                .comentario("Este es un comentario")
                .build();
        Mockito.when(comentarioRepository.save(comInput)).thenReturn(comOutput);

        ComentarioEntity rta = comentarioService.guardarComentario(comInput);

        Assert.assertNotNull(rta);
    }
    @Test
    public void testGetComentariosByArticuloId(){
        Long idArt = Long.valueOf("1");
        List<ComentarioEntity> rta =  comentarioService.getComentariosByArticuloId(idArt);
        Assert.assertNull(rta);
    }

    @Test
    public void testObtenerComentariosByArticulo(){
        Long idIdea = Long.valueOf("1");
        ComentarioEntity comentarioUno = ComentarioEntity.builder().build();
        ComentarioEntity comentarioDos = ComentarioEntity.builder().build();
        ArrayList<ComentarioEntity> listRta = new ArrayList<>();
        listRta.add(comentarioUno);
        listRta.add(comentarioDos);
        Mockito.when(comentarioRepository.getListByIdeaId(idIdea)).thenReturn(listRta);

        List<ComentarioEntity> rta = comentarioService.obtenerComentariosByArticulo(idIdea);
        Assert.assertNotNull(rta);
    }
}
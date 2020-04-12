package co.com.ud.service.adm.impl;

import co.com.ud.adm.dto.TokenDto;
import co.com.ud.persistence.entity.UsuarioEntity;
import co.com.ud.persistence.entity.enumeracion.TipoUsuario;
import co.com.ud.persistence.repository.IUsuarioRepository;
import co.com.ud.service.adm.ITokenService;
import co.com.ud.service.adm.IUsuarioService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UsuarioServiceTest {

    private IUsuarioService usuarioService;
    @Mock
    private IUsuarioRepository usuarioRepository;
    @Mock
    private ITokenService tokenService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.usuarioService = new UsuarioService(usuarioRepository, tokenService);
    }

    @Test
    public void testGeneraToken() {
        UsuarioEntity usuarioInput = UsuarioEntity.builder()
                .nombre("Jesus Nicolas")
                .correo("jnsierrac@gmail.com")
                .build();
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
        Mockito.when(tokenService.generateToken("jnsierrac@gmail.com", grantedAuthorities)).thenReturn("12345");
        Optional<TokenDto> token = usuarioService.generaToken(usuarioInput);
    }

    @Test
    public void testUpdateTipoUsuarioSUCCESS() {
        Long idUsuario = Long.valueOf(1);
        Mockito.when(usuarioRepository.updateTipoUsuario(TipoUsuario.ADMIN, idUsuario)).thenReturn(Integer.valueOf("1"));
        Boolean valida = usuarioService.updateTipoUsuario(TipoUsuario.ADMIN, Long.valueOf("1"));
        Assert.assertEquals(Boolean.TRUE, valida);
    }

    @Test
    public void testUpdateTipoUsuarioFAILED() {
        Long idUsuario = Long.valueOf(1);
        Mockito.when(usuarioRepository.updateTipoUsuario(TipoUsuario.ADMIN, idUsuario)).thenReturn(Integer.valueOf("1"));
        Boolean valida = usuarioService.updateTipoUsuario(TipoUsuario.ADMIN, Long.valueOf("0"));
        Assert.assertEquals(Boolean.FALSE, valida);
    }

    @Test
    public void testLoginUser(){
        String usuario = "jnsierrac@gmail.com";
        String pass = "123456";
        Optional<UsuarioEntity> usuarioOutput = Optional.of(UsuarioEntity.builder()
                .id(Long.valueOf("1"))
                .correo("jnsierrac@gmail.com")
                .contrasena("123456")
                .build());
        Mockito.when(usuarioRepository.findByCorreoAndContrasenaAllIgnoreCase(usuario, pass)).thenReturn(usuarioOutput);

        Optional<UsuarioEntity> rta = usuarioService.loginUser(usuario,pass);
        Assert.assertNotNull(rta);
        Assert.assertEquals(Boolean.TRUE, rta.isPresent());
    }

    @Test
    public void testInsertarUsuario(){

        UsuarioEntity usuarioInput = UsuarioEntity.builder()
                .nombre("Jesus Nicolas")
                .tipoUsuario(TipoUsuario.ADMIN)
                .correo("jnsierrac@gmail.com")
                .contrasena("123456")
                .build();

        UsuarioEntity usuarioOutput = UsuarioEntity.builder()
                .id(Long.valueOf(1))
                .nombre("Jesus Nicolas")
                .tipoUsuario(TipoUsuario.ADMIN)
                .correo("jnsierrac@gmail.com")
                .contrasena("123456")
                .build();

        Mockito.when(usuarioRepository.save(usuarioInput)).thenReturn(usuarioOutput);

        UsuarioEntity rta = usuarioService.insertarUsuario(usuarioInput);

        Assert.assertNotNull(rta);
    }

    @Test
    public void testGetByCorreo(){
        UsuarioEntity usuarioOutput = UsuarioEntity.builder()
                .nombre("Jesus Nicolas")
                .tipoUsuario(TipoUsuario.ADMIN)
                .correo("jnsierrac@gmail.com")
                .contrasena("123456")
                .build();
        List<UsuarioEntity> array = new ArrayList<>();
        array.add(usuarioOutput);
        Mockito.when(usuarioService.getByCorreo("jnsierrac@gmail.com")).thenReturn(array);

        List<UsuarioEntity> rta =usuarioService.getByCorreo("jnsierrac@gmail.com");

        Assert.assertNotNull(rta);
    }
}
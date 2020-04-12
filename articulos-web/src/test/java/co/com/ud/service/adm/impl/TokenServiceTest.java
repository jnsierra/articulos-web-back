package co.com.ud.service.adm.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

@SpringBootTest
public class TokenServiceTest {

    private TokenService tokenService;

    @Before
    public void setUp(){
        tokenService = new TokenService("1234567890");
    }
    @Test
    public void testGenerateToken(){
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
        String rta = tokenService.generateToken("jnsierrac@gmail.com", grantedAuthorities);
        Assert.assertNotNull(rta);
    }
}
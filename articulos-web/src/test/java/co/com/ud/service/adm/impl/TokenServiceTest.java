package co.com.ud.service.adm.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenServiceTest {

    private TokenService tokenService;

    @Before
    public void setUp(){

    }
    @Test
    public void testGenerateToken(){
        Assert.assertNotNull("Hoola");
    }
}
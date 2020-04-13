package co.com.ud.controller.integration.steps;

import co.com.ud.bussines.service.adm.dto.TokenDto;
import co.com.ud.controller.adm.dto.UsuarioDto;
import co.com.ud.controller.integration.config.ContextLoader;
import co.com.ud.controller.integration.config.HttpClient;
import com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class LoginStep extends ContextLoader {

    @Autowired
    private HttpClient httpClient;
    private String USUARIO;
    private String CONTRASENIA;
    private String TOKEN;
    private String rtaService;

    private final Logger log = LoggerFactory.getLogger(LoginStep.class);

    @Given("^realizamos el ingreso con el sigiente usurio$")
    public void givenUser(DataTable payload){
        List<Map<String, String>> rows = payload.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            USUARIO = columns.get("usuario");
            CONTRASENIA = columns.get("contrasenia");
        }
    }

    @When("^envio la informacion al servicio de login$")
    public void login(){
        UsuarioDto usuarioLogin = UsuarioDto.builder()
                .correo(USUARIO)
                .contrasena(CONTRASENIA)
                .build();
        ResponseEntity<String> rta = httpClient.postWithRequest("/login/",usuarioLogin);
        Assert.assertNotNull(rta);
        Assert.assertEquals(HttpStatus.OK, rta.getStatusCode());
        rtaService = rta.getBody();
        Assert.assertNotNull(rtaService);
    }

    @Then("debo tener un token de autenticacion {string}")
    public void validoToken(String token){
        TokenDto tokenDto = new Gson().fromJson(rtaService, TokenDto.class);
        Assert.assertNotNull(tokenDto);
        Assert.assertNotNull(tokenDto.getToken());
    }
}

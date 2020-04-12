package co.com.ud.controller.integration.steps;

import co.com.ud.controller.integration.config.ContextLoader;
import co.com.ud.controller.integration.config.HttpClient;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginStep extends ContextLoader {

    @Autowired
    private HttpClient httpClient;

    private final Logger log = LoggerFactory.getLogger(LoginStep.class);

    @Given("^realizamos el ingreso con el sigiente usurio$")
    public void givenUser(DataTable payload){

        log.info("Llego 001");
    }

    @When("^envio la informacion al servicio de login$")
    public void login(){
        log.info("Llego 002");
        Assert.assertNotNull("Hola");
    }

    @Then("debo tener un token de autenticacion {string}")
    public void validoToken(String token){
        log.info("Este es el then {}",token);
        Assert.assertNotNull("Hola");
    }
}

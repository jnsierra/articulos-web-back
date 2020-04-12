package co.com.ud.bussines.service.auth;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {

    Authentication getAuthentication();

    String getAuthName();

}

package co.com.ud.service.auth;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {

    Authentication getAuthentication();

    String getAuthName();

}

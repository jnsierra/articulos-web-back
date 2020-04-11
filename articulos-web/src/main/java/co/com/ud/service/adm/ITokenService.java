package co.com.ud.service.adm;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public interface ITokenService {
	
	/**
	 * Metodo con el cual se genera el token jwt para su posterior llamado a
	 * servicos
	 * 
	 * @param user
	 * @param grantedAuthorities
	 * @return
	 */
	String generateToken(String user, List<GrantedAuthority> grantedAuthorities);

}

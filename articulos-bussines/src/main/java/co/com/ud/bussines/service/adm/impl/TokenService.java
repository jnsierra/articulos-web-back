package co.com.ud.bussines.service.adm.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import co.com.ud.bussines.service.adm.ITokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Service
public class TokenService implements ITokenService {

	private final String secret;
	public static Integer seconds = Integer.valueOf("3600") ;

	@Autowired
	public TokenService(@Value("${jwt.secret}")String secret) {
		this.secret = secret;
	}
	
	@Override
	public String generateToken(String user, List<GrantedAuthority> grantedAuthorities) {
		String jwt = "";
		Instant now = Instant.now();

		jwt = Jwts.builder().setSubject(user)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(Date.from(now)).setExpiration(Date.from(now.plus(seconds, ChronoUnit.SECONDS)))
				.signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.encode(secret)).compact();
		return jwt;
	}

}

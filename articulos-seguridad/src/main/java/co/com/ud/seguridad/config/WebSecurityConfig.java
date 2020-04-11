package co.com.ud.seguridad.config;

import co.com.ud.seguridad.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${jwt.secret}")
	private String secret;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.addFilterAfter(getJwtFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/login/", "/v.1/usuarios/", "/v.1/estadisticas/ideas/estado/","/v.1/estadisticas/articulo/estado/" )
			.permitAll()
			.anyRequest()
			.authenticated();
	}

	@Bean
	public JwtFilter getJwtFilter(){
		return new JwtFilter(secret);
	}
	
	@Bean
	public FilterRegistrationBean simpleCorsFilter() {  
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
	    CorsConfiguration config = new CorsConfiguration();  
	    config.setAllowCredentials(true); 
	    // *** URL below needs to match the Vue client URL and port ***
	    config.setAllowedOrigins(Collections.singletonList("*")); 
	    config.setAllowedMethods(Collections.singletonList("*"));  
	    config.setAllowedHeaders(Collections.singletonList("*"));  
	    source.registerCorsConfiguration("/**", config);  
	    FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
	    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);  
	    return bean;  
	} 



}
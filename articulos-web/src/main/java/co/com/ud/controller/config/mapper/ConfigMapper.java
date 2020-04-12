package co.com.ud.controller.config.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ConfigMapper {

	@Bean
	@Primary
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}

package com.algaworks.algafood;

import java.util.TimeZone;

import com.algaworks.algafood.core.security.authorizationserver.AlgaFoodSecurityProperties;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ConfigurationPropertiesScan(basePackageClasses = AlgaFoodSecurityProperties.class)
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}

}

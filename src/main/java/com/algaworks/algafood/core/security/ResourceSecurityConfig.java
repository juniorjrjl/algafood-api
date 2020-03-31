package com.algaworks.algafood.core.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@EnableWebSecurity
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter{


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        		.anyRequest().authenticated()
        	.and()
        	.cors().and()
        	.oauth2ResourceServer().jwt();
                
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {
    	var secretKey = new SecretKeySpec("fsd8a7f9f47d98sa7f1sd9c879fcasdf71c8sd97cf8sad97f1csd9817cfsd977f1cd97sad19sd".getBytes(), "HmacSHA256");
    	return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

}
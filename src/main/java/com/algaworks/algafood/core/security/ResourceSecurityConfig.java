package com.algaworks.algafood.core.security;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter{


	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
			.formLogin()
			.loginPage("/login")
			.and()
				.authorizeRequests()
					.antMatchers("/oauth/**").authenticated()
			.and()
				.csrf().disable()
				.cors()
			.and()
				.oauth2ResourceServer().jwt()
					.jwtAuthenticationConverter(jwtAuthenticationConverter());
	}

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
    	return super.authenticationManager();
    }
    
    
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
    	var jwtAuthenticationConverter = new JwtAuthenticationConverter();
    	jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt ->{
    		var authtorities = jwt.getClaimAsStringList("authorities");
    		if (authtorities == null) {
    			authtorities = Collections.emptyList();
    		}
    		var scopeAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    		Collection<GrantedAuthority> grantedAuthorities = scopeAuthoritiesConverter.convert(jwt);
    		grantedAuthorities.addAll(authtorities.stream()
    				.map(SimpleGrantedAuthority::new)
    				.collect(Collectors.toList()));
    		return grantedAuthorities;
    	});
    	return jwtAuthenticationConverter;
    }
}
package com.algaworks.algafood.core.squiggly;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(final ObjectMapper objectMapper){
        Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null));

        var urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");

        var filterRegistrarion = new FilterRegistrationBean<SquigglyRequestFilter>();
        filterRegistrarion.setFilter(new SquigglyRequestFilter());
        filterRegistrarion.setOrder(1);
        filterRegistrarion.setUrlPatterns(urlPatterns);
        return filterRegistrarion;
    }
    
}
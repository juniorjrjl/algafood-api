package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties("algafood.auth")
@ConstructorBinding
@Validated
public record AlgaFoodSecurityProperties(@NotBlank String providerUrl) { }

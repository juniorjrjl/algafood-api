package com.algaworks.algafood.core.security.authorizationserver;

import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyStore;
import java.util.HashSet;
import java.util.Set;

@Configuration
@AllArgsConstructor
public class AuthorizationServerConfig {

    private final AlgaFoodSecurityProperties algaFoodSecurityProperties;
    private final PasswordEncoder passwordEncoder;
    private final JdbcOperations jdbcOperations;
    private final JwtKeyStoreProperties properties;
    private final UsuarioRepository usuarioRepository;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();

        authorizationServerConfigurer.authorizationEndpoint(c -> c.consentPage("/oauth2/consent"));

        RequestMatcher endpointsMatcher = authorizationServerConfigurer
                .getEndpointsMatcher();

        http.requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer);
        return http.formLogin(c -> c.loginPage("/login")).build();
    }

    @Bean
    public ProviderSettings providerSettings(){
        return ProviderSettings.builder().issuer(algaFoodSecurityProperties.providerUrl()).build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(){
        return new JdbcRegisteredClientRepository(jdbcOperations);
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(final RegisteredClientRepository registeredClientRepository){
        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        var keystorePass = properties.getPassword().toCharArray();
        var keypairAlias = properties.getKeypairAlias();

        var jwsLocation = properties.getJksLocation();
        var inputStream = jwsLocation.getInputStream();
        var keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, keystorePass);

        var rsaKey = RSAKey.load(keyStore, keypairAlias, keystorePass);
        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(){
        return context -> {
            var authentication = context.getPrincipal();
            if (authentication.getPrincipal() instanceof User user){
                var usuario = usuarioRepository.findByEmail(user.getUsername());
                Set<String> authorities = new HashSet<>();
                user.getAuthorities().forEach(a -> authorities.add(a.getAuthority()));
                usuario.ifPresent(u ->{
                    context.getClaims().claim("usuario_id", u.getId().toString());
                    context.getClaims().claim("authorities", authorities);
                });

            }
        };
    }

    @Bean
    public OAuth2AuthorizationConsentService consentService(final RegisteredClientRepository registeredClientRepository){
        return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
    }

}

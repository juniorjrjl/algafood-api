package com.algaworks.algafood.core.security.authorizationserver;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Controller
@AllArgsConstructor
public class AuthorizationConsentController {

    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationConsentService consentService;

    @GetMapping("/oauth2/consent")
    public String consent(final Principal principal, final Model model,
                          @RequestParam(OAuth2ParameterNames.CLIENT_ID) final String clientId,
                          @RequestParam(OAuth2ParameterNames.SCOPE) final String scope,
                          @RequestParam(OAuth2ParameterNames.STATE)final String state){
        var client = this.registeredClientRepository.findByClientId(clientId);
        if (Objects.isNull(client)){
            throw new AccessDeniedException(String.format("Cliente de %s não foi encontrado", clientId));
        }
        var consent = this.consentService.findById(client.getId(), principal.getName());

        var scopeArray = scope.split(" ");
        var scopesParaAprovar = new HashSet<>(Set.of(scopeArray));

        Set<String> scopesAprovadosAnteriormente;
        if (Objects.nonNull(consent)){
            scopesAprovadosAnteriormente = consent.getScopes();
            scopesParaAprovar.removeAll(scopesAprovadosAnteriormente);
        }else {
            scopesAprovadosAnteriormente = Collections.emptySet();
        }

        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);
        model.addAttribute("principalName", principal.getName());
        model.addAttribute("scopesParaAprovar", scopesParaAprovar);
        model.addAttribute("scopesAprovadosAnteriormente", scopesAprovadosAnteriormente);

        return "pages/approval";
    }

}

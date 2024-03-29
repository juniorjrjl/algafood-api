package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CozinhaController implements CozinhaControllerOpenApi{

    private final CadastroCozinhaService cadastroCozinha;

    private final CozinhaInputDisassembler cozinhaInputDisassembler;

    private final CozinhaModelAssembler cozinhaModelAssembler;

    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping
    public PagedModel<CozinhaModel> listar(final Pageable pageable) {
        log.info("consultando cozinhas com páginas de {} registros", pageable.getPageSize());
        Page<Cozinha> cozinhasPage = cadastroCozinha.listar(pageable);
        PagedModel<CozinhaModel> cozinhaPagedModel = pagedResourcesAssembler
            .toModel(cozinhasPage, cozinhaModelAssembler);
        return cozinhaPagedModel;
    }

    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping("{idCozinha}")
    public CozinhaModel buscar(@PathVariable final Long idCozinha){
        return cozinhaModelAssembler.toModel(cadastroCozinha.buscar(idCozinha));
    }
    
    @CheckSecurity.Cozinhas.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid final CozinhaInput cozinhaInput){
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @PutMapping("{idCozinha}")
    public CozinhaModel atualizar(@PathVariable final Long idCozinha, @RequestBody @Valid final CozinhaInput cozinhaInput){
        Cozinha cozinhaAtual = cadastroCozinha.buscar(idCozinha);
        cozinhaInputDisassembler.copyToDomainInObject(cozinhaInput, cozinhaAtual);
        return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @DeleteMapping("{idCozinha}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable final Long idCozinha){
        cadastroCozinha.excluir(idCozinha);
    }

}
package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.v2.assembler.CozinhaInputDisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CozinhaModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CozinhaControllerV2OpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import lombok.AllArgsConstructor;
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

@RestController
@RequestMapping(path = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi{

    private final CadastroCozinhaService cadastroCozinha;

    private final CozinhaInputDisassemblerV2 cozinhaInputDisassembler;

    private final CozinhaModelAssemblerV2 cozinhaModelAssembler;

    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping
    public PagedModel<CozinhaModelV2> listar(final Pageable pageable){
        Page<Cozinha> cozinhasPage = cadastroCozinha.listar(pageable);
        PagedModel<CozinhaModelV2> cozinhaPagedModel = pagedResourcesAssembler
            .toModel(cozinhasPage, cozinhaModelAssembler);
        return cozinhaPagedModel;
    }

    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping("{idCozinha}")
    public CozinhaModelV2 buscar(@PathVariable final Long idCozinha){
        return cozinhaModelAssembler.toModel(cadastroCozinha.buscar(idCozinha));
    }
    
    @CheckSecurity.Cozinhas.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelV2 adicionar(@RequestBody @Valid final CozinhaInputV2 CozinhaInputV2){
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(CozinhaInputV2);
        return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @PutMapping("{idCozinha}")
    public CozinhaModelV2 atualizar(@PathVariable final Long idCozinha, @RequestBody @Valid final CozinhaInputV2 CozinhaInputV2){
        Cozinha cozinhaAtual = cadastroCozinha.buscar(idCozinha);
        cozinhaInputDisassembler.copyToDomainInObject(CozinhaInputV2, cozinhaAtual);
        return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @DeleteMapping("{idCozinha}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable final Long idCozinha){
        cadastroCozinha.excluir(idCozinha);
    }

}
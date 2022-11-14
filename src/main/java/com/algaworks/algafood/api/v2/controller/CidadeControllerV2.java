package com.algaworks.algafood.api.v2.controller;

import javax.validation.Valid;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CidadeControllerV2OpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CidadeControllerV2 implements CidadeControllerV2OpenApi{

    private final CidadeInputDisassemblerV2 cidadeInputDisassembler;

    private final CidadeModelAssemblerV2 cidadeModelAssembler;

    private final CadastroCidadeService cadastroCidade;

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping
    public CollectionModel<CidadeModelV2> listar() {
        return cidadeModelAssembler.toCollectionModel(cadastroCidade.listar());
    }

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping("{idCidade}")
    public CidadeModelV2 buscar(@PathVariable final Long idCidade) {
        return cidadeModelAssembler.toModel(cadastroCidade.buscar(idCidade));
    }

    @CheckSecurity.Cidades.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModelV2 adicionar(@RequestBody @Valid final CidadeInputV2 cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getIdCidade());
            return cidadeModel;
        }catch(EstadoNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @PutMapping("{idCidade}")
    public CidadeModelV2 atualizar(@PathVariable final Long idCidade, @RequestBody @Valid final CidadeInputV2 cidadeInput){
        try{
            Cidade cidadeAtual = cadastroCidade.buscar(idCidade);
            cidadeInputDisassembler.copyToDomainInObject(cidadeInput, cidadeAtual);
            return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
        }catch(EstadoNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(), ex);
        }

    }
    
    @CheckSecurity.Cidades.PodeEditar
    @DeleteMapping("{idCidade}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable final Long idCidade){
        cadastroCidade.excluir(idCidade);
    }

}
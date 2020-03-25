package com.algaworks.algafood.api.v2.controller;

import javax.validation.Valid;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CidadeControllerV2OpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

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
public class CidadeControllerV2 implements CidadeControllerV2OpenApi{

    @Autowired
    private CidadeInputDisassemblerV2 cidadeInputDisassembler;

    @Autowired
    private CidadeModelAssemblerV2 cidadeModelAssembler;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @GetMapping
    public CollectionModel<CidadeModelV2> listar() {
        return cidadeModelAssembler.toCollectionModel(cadastroCidade.listar());
    }

    @GetMapping("{id}")
    public CidadeModelV2 buscar(@PathVariable Long id) {
        return cidadeModelAssembler.toModel(cadastroCidade.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
            ResourceUriHelper.addUriInresponseHeader(cidadeModel.getIdCidade());
            return cidadeModel;
        }catch(EstadoNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @PutMapping("{id}")
    public CidadeModelV2 atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInputV2 cidadeInput){
        try{
            Cidade cidadeAtual = cadastroCidade.buscar(id);
            cidadeInputDisassembler.copyToDomainInObject(cidadeInput, cidadeAtual);
            return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
        }catch(EstadoNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(), ex);
        }

    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroCidade.excluir(id);
    }

}
package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @GetMapping
    public List<CidadeModel> listar(){
        return cidadeModelAssembler.toCollectionModel(cadastroCidade.listar());
    }

    @GetMapping("{id}")
    public CidadeModel buscar(@PathVariable Long id){
        return cidadeModelAssembler.toModel(cadastroCidade.buscar(id));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput){
        try{
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
        }catch(EstadoNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @PutMapping("{id}")
    public CidadeModel atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput){
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
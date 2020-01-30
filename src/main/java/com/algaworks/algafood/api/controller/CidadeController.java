package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import org.springframework.beans.BeanUtils;
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
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar(){
        return cadastroCidadeService.listar();
    }

    @GetMapping("{id}")
    public Cidade buscar(@PathVariable Long id){
        return cadastroCidadeService.buscar(id);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody Cidade cidade){
        try{
            return cadastroCidadeService.salvar(cidade);
        }catch(EstadoNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @PutMapping("{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidade){
        try{
            Cidade cidadeAtual = cadastroCidadeService.buscar(id);
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            return cadastroCidadeService.salvar(cidadeAtual);
        }catch(EstadoNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(), ex);
        }

    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroCidadeService.excluir(id);
    }

}
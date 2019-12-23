package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Cidade> buscar(@PathVariable Long id){
        Cidade cidade = cadastroCidadeService.buscar(id);
        return cidade == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(cidade);
    }
    
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade){
        try{
            cidade = cadastroCidadeService.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        }catch(EntidadeNaoEncontradaException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade){
        try{
            Cidade cidadeAtual = cadastroCidadeService.buscar(id);
            if (cidadeAtual == null){
                return ResponseEntity.notFound().build();
            }
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            cadastroCidadeService.salvar(cidadeAtual);
            return ResponseEntity.ok(cidadeAtual);
        }catch(EntidadeNaoEncontradaException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Cidade> remover(@PathVariable Long id){
        try{
            cadastroCidadeService.excluir(id);
            return ResponseEntity.noContent().build();
        }catch(EntidadeNaoEncontradaException ex){
            return ResponseEntity.notFound().build();
        }
    }

}
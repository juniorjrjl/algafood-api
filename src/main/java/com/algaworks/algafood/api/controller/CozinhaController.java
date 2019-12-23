package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Cozinha> listar(){
        return cadastroCozinhaService.listar();
    }

    @GetMapping("{id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long id){
        Optional<Cozinha> cozinha = cadastroCozinhaService.buscar(id);
        return cozinha.isPresent() ? ResponseEntity.ok(cozinha.get()) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("{id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha){
        Optional<Cozinha> cozinhaAtual = cadastroCozinhaService.buscar(id);
        if (cozinhaAtual.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
        Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual.get());
        return ResponseEntity.ok(cozinhaSalva);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long id){
        try{
            cadastroCozinhaService.excluir(id);
            return ResponseEntity.noContent().build();
        }catch(EntidadeNaoEncontradaException ex){
            return ResponseEntity.notFound().build();
        }catch(EntidadeEmUsoException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
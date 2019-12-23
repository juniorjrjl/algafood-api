package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

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
@RequestMapping("/estados")
public class EstadoController {


    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar(){
        return cadastroEstadoService.listar();
    }

    @GetMapping("{id}")
    public ResponseEntity<Estado> buscar(@PathVariable Long id){
        Optional<Estado> cozinha = cadastroEstadoService.buscar(id);
        return cozinha.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(cozinha.get());
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado){
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estado){
        Optional<Estado> estadoAtual = cadastroEstadoService.buscar(id);
        if (estadoAtual == null){
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
        Estado estadoSalvo = cadastroEstadoService.salvar(estadoAtual.get());
        return ResponseEntity.ok(estadoSalvo);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Estado> remover(@PathVariable Long id){
        try{
            cadastroEstadoService.excluir(id);
            return ResponseEntity.noContent().build();
        }catch(EntidadeNaoEncontradaException ex){
            return ResponseEntity.notFound().build();
        }catch(EntidadeEmUsoException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
}
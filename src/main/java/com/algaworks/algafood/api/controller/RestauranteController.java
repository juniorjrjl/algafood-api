package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar(){
        return cadastroRestauranteService.listar();
    }

    @GetMapping("{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id){
        Restaurante restaurante = cadastroRestauranteService.buscar(id);
        return restaurante == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(restaurante);
    }
    
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
        try{
            restaurante = cadastroRestauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        }catch(EntidadeNaoEncontradaException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante){
        try{
            Restaurante restauranteAtual = cadastroRestauranteService.buscar(id);
            if (restauranteAtual == null){
                return ResponseEntity.notFound().build();
            }
            BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
            cadastroRestauranteService.salvar(restauranteAtual);
            return ResponseEntity.ok(restauranteAtual);
        }catch(EntidadeNaoEncontradaException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
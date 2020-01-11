package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
        Optional<Restaurante> restaurante = cadastroRestauranteService.buscar(id);
        return restaurante.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(restaurante.get());
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
            Optional<Restaurante> restauranteAtual = cadastroRestauranteService.buscar(id);
            if (restauranteAtual.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
            Restaurante restauranteSalvo= cadastroRestauranteService.salvar(restauranteAtual.get());
            return ResponseEntity.ok(restauranteSalvo);
        }catch(EntidadeNaoEncontradaException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object>campos){
        Optional<Restaurante> restauranteAtual = cadastroRestauranteService.buscar(id);
        if (restauranteAtual.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        merge(campos, restauranteAtual.get());
        return atualizar(id, restauranteAtual.get());
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
        campos.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }

    @GetMapping("frete-gratis")
    public List<Restaurante> restaurantesFreteGratis(String nome){
        return cadastroRestauranteService.listarFreteGratis(nome);

    }

}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private SmartValidator validator;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar(){
        return cadastroRestauranteService.listar();
    }

    @GetMapping("{id}")
    public Restaurante buscar(@PathVariable Long id){
        return cadastroRestauranteService.buscar(id);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante){
        try{
            return cadastroRestauranteService.salvar(restaurante);
        }catch(CozinhaNaoEncontradaException ex){
            throw new NegocioException(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody @Valid Restaurante restaurante){
        Restaurante restauranteAtual = cadastroRestauranteService.buscar(id);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", 
            "formasPagamento", "endereco", "dataCadastro", "produtos");
        try{
            return cadastroRestauranteService.salvar(restauranteAtual);
        }catch(CozinhaNaoEncontradaException ex){
            throw new NegocioException(ex.getMessage());
        }
    }

    @PatchMapping("{id}")
    public Restaurante atualizarParcial(@PathVariable Long id, 
        @RequestBody Map<String, Object>campos, HttpServletRequest request){
        Restaurante restauranteAtual = cadastroRestauranteService.buscar(id);
        merge(campos, restauranteAtual, request);
        validate(restauranteAtual, "restaurante");
        return atualizar(id, restauranteAtual);
    }

    private void validate(Restaurante restaurante, String objectName){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);
        if (bindingResult.hasErrors()){
            throw new ValidacaoException(bindingResult);
        }
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteDestino, 
        HttpServletRequest request) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);


            Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
            campos.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        }catch(IllegalArgumentException ex){
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, 
                new ServletServerHttpRequest(request));
        }
    }

    @GetMapping("frete-gratis")
    public List<Restaurante> restaurantesFreteGratis(String nome){
        return cadastroRestauranteService.listarFreteGratis(nome);

    }

}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
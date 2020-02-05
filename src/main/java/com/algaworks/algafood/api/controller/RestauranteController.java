package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
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

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @GetMapping
    public List<RestauranteModel> listar(){
        return restauranteModelAssembler.toCollectionModel(cadastroRestauranteService.listar());
    }

    @GetMapping("{id}")
    public RestauranteModel buscar(@PathVariable Long id){
        return restauranteModelAssembler.toModel(cadastroRestauranteService.buscar(id));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput){
        try{
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
        }catch(CozinhaNaoEncontradaException ex){
            throw new NegocioException(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public RestauranteModel atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput){
        Restaurante restauranteAtual = cadastroRestauranteService.buscar(id);
        Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", 
            "formasPagamento", "endereco", "dataCadastro", "produtos");
        try{
            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
        }catch(CozinhaNaoEncontradaException ex){
            throw new NegocioException(ex.getMessage());
        }
    }

    @PatchMapping("{id}")
    public RestauranteModel atualizarParcial(@PathVariable Long id, 
        @RequestBody Map<String, Object>campos, HttpServletRequest request){
        Restaurante restauranteAtual = cadastroRestauranteService.buscar(id);
        merge(campos, restauranteAtual, request);
        validate(restauranteAtual, "restaurante");
        return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
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
    public List<RestauranteModel> restaurantesFreteGratis(String nome){
        return restauranteModelAssembler.
            toCollectionModel(cadastroRestauranteService.listarFreteGratis(nome));

    }

}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
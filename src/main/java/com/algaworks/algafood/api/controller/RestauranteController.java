package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @GetMapping
    public List<RestauranteModel> listar(){
        return restauranteModelAssembler.toCollectionModel(cadastroRestaurante.listar());
    }

    @GetMapping("{id}")
    public RestauranteModel buscar(@PathVariable Long id){
        return restauranteModelAssembler.toModel(cadastroRestaurante.buscar(id));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput){
        try{
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
        }catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex){
            throw new NegocioException(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public RestauranteModel atualizar(@PathVariable Long id, 
        @RequestBody @Valid RestauranteInput restauranteInput){
        try{
            Restaurante restauranteAtual = cadastroRestaurante.buscar(id);
            restauranteInputDisassembler.copyToDomainInObject(restauranteInput, restauranteAtual);
            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
        }catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex){
            throw new NegocioException(ex.getMessage());
        }
    }

    @PatchMapping("{id}")
    public RestauranteModel atualizarParcial(@PathVariable Long id, 
        @RequestBody Map<String, Object>campos, HttpServletRequest request){
        Restaurante restauranteAtual = cadastroRestaurante.buscar(id);
        restauranteInputDisassembler.copyToDomainInObject(campos, restauranteAtual);
        validate(restauranteAtual, "restaurante");
        return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
    }

    private void validate(Restaurante restaurante, String objectName){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);
        if (bindingResult.hasErrors()){
            throw new ValidacaoException(bindingResult);
        }
    }

    @GetMapping("frete-gratis")
    public List<RestauranteModel> restaurantesFreteGratis(String nome){
        return restauranteModelAssembler.
            toCollectionModel(cadastroRestaurante.listarFreteGratis(nome));
    }

    @PutMapping("{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativatr(@PathVariable Long id){
        cadastroRestaurante.ativar(id);
    }

    @DeleteMapping("{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativatr(@PathVariable Long id){
        cadastroRestaurante.inativar(id);
    }

    @PutMapping("{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long id){
        cadastroRestaurante.abrir(id);
    }

    @PutMapping("{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long id){
        cadastroRestaurante.fechar(id);
    }

}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
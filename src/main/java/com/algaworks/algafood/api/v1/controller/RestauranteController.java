package com.algaworks.algafood.api.v1.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(path = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi{

    @Autowired
    private SmartValidator validator;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

    @Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;

    @Autowired
	private AlgaLinks algaLinks;

    @GetMapping
    public CollectionModel<RestauranteBasicoModel> listarResumido(){
        return restauranteBasicoModelAssembler.toCollectionModel(cadastroRestaurante.listar());
    }

    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNome(){
        return restauranteApenasNomeModelAssembler.toCollectionModel(cadastroRestaurante.listar());
    }

    @GetMapping("{id}")
    public RestauranteModel buscar(@PathVariable Long id){
        return restauranteModelAssembler
            .toModel(cadastroRestaurante.buscar(id))
            .add(algaLinks.linkToRestauranteProdutos("produtos", id));
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

    @PutMapping("{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long id){
        cadastroRestaurante.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@RequestBody List<Long> restaurantesIds){
        try{
            cadastroRestaurante.ativar(restaurantesIds);
            return ResponseEntity.noContent().build();
        }catch(RestauranteNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @DeleteMapping("{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long id){
        cadastroRestaurante.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@RequestBody List<Long> restaurantesIds){
        try{
            cadastroRestaurante.inativar(restaurantesIds);
            return ResponseEntity.noContent().build();
        }catch(RestauranteNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @PutMapping("{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long id){
        cadastroRestaurante.abrir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long id){
        cadastroRestaurante.fechar(id);
        return ResponseEntity.noContent().build();
    }

}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
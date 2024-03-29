package com.algaworks.algafood.api.v1.controller;

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
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class RestauranteController implements RestauranteControllerOpenApi{

    private final SmartValidator validator;

    private final CadastroRestauranteService cadastroRestaurante;

    private final RestauranteInputDisassembler restauranteInputDisassembler;

    private final RestauranteModelAssembler restauranteModelAssembler;

    private final RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

	private final RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;

	private final AlgaLinks algaLinks;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<RestauranteBasicoModel> listarResumido(){
        return restauranteBasicoModelAssembler.toCollectionModel(cadastroRestaurante.listar());
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNome(){
        return restauranteApenasNomeModelAssembler.toCollectionModel(cadastroRestaurante.listar());
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("{idRestaurante}")
    public RestauranteModel buscar(@PathVariable final Long idRestaurante){
        return restauranteModelAssembler
            .toModel(cadastroRestaurante.buscar(idRestaurante))
            .add(algaLinks.linkToRestauranteProdutos("produtos", idRestaurante));
    }
    
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid final RestauranteInput restauranteInput){
        try{
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
        }catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex){
            throw new NegocioException(ex.getMessage());
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("{idRestaurante}")
    public RestauranteModel atualizar(@PathVariable final Long idRestaurante,
                                      @RequestBody @Valid final RestauranteInput restauranteInput){
        try{
            Restaurante restauranteAtual = cadastroRestaurante.buscar(idRestaurante);
            restauranteInputDisassembler.copyToDomainInObject(restauranteInput, restauranteAtual);
            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
        }catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex){
            throw new NegocioException(ex.getMessage());
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PatchMapping("{idRestaurante}")
    public RestauranteModel atualizarParcial(@PathVariable final Long idRestaurante,
                                             @RequestBody final Map<String, Object>campos,
                                             final HttpServletRequest request){
        Restaurante restauranteAtual = cadastroRestaurante.buscar(idRestaurante);
        restauranteInputDisassembler.copyToDomainInObject(campos, restauranteAtual);
        validate(restauranteAtual, "restaurante");
        return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
    }

    private void validate(final Restaurante restaurante, final String objectName){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);
        if (bindingResult.hasErrors()){
            throw new ValidacaoException(bindingResult);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("{idRestaurante}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable final Long idRestaurante){
        cadastroRestaurante.ativar(idRestaurante);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@RequestBody final List<Long> restaurantesIds){
        try{
            cadastroRestaurante.ativar(restaurantesIds);
            return ResponseEntity.noContent().build();
        }catch(RestauranteNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
    
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("{idRestaurante}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable final Long idRestaurante){
        cadastroRestaurante.inativar(idRestaurante);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@RequestBody final List<Long> restaurantesIds){
        try{
            cadastroRestaurante.inativar(restaurantesIds);
            return ResponseEntity.noContent().build();
        }catch(RestauranteNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("{idRestaurante}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable final Long idRestaurante){
        cadastroRestaurante.abrir(idRestaurante);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("{idRestaurante}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable final Long idRestaurante){
        cadastroRestaurante.fechar(idRestaurante);
        return ResponseEntity.noContent().build();
    }

}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
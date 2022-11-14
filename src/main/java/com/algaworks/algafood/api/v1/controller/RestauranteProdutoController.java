package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi{

    private final CadastroProdutoService cadastroProduto;

    private final ProdutoInputDisassembler produtoInputDisassembler;

    private final ProdutoModelAssembler produtoModelAssembler;

	private final AlgaLinks algaLinks;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<ProdutoModel> listar(@PathVariable final Long restauranteId,
           @RequestParam(required = false, defaultValue = "false") final Boolean incluirInativos){
        List<Produto> produtos = cadastroProduto.buscar(restauranteId, incluirInativos);
        return produtoModelAssembler.toCollectionModel(produtos)
            .add(algaLinks.linkToRestauranteProdutos(IanaLinkRelations.SELF.value(), restauranteId));
    }
    
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable final Long restauranteId, @PathVariable final Long produtoId){
        Produto produto = cadastroProduto.buscar(restauranteId, produtoId);
        return produtoModelAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel cadastrar(@PathVariable final Long restauranteId,
                                  @RequestBody @Valid final ProdutoInput produtoInput) {
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.getRestaurante().setId(restauranteId);
        return produtoModelAssembler.toModel(cadastroProduto.salvar(produto));
    }
    
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable final Long restauranteId,
                                  @PathVariable final Long produtoId,
                                  @RequestBody final ProdutoInput produtoInput) {
        Produto produto = cadastroProduto.buscar(restauranteId, produtoId);
        produtoInputDisassembler.copyToDomainInObject(produtoInput, produto);
        return produtoModelAssembler.toModel(cadastroProduto.salvar(produto));
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable final Long restauranteId, @PathVariable final Long produtoId){
        cadastroProduto.remover(restauranteId, produtoId);
    }

}
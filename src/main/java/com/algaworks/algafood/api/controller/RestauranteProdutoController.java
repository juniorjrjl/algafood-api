package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId){
        Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
        return produtoModelAssembler.toCollectionModel(restaurante.getProdutos());
    }
    
    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        Produto produto = cadastroProduto.buscar(restauranteId, produtoId);
        return produtoModelAssembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel cadastrar(@PathVariable Long restauranteId, 
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.getRestaurante().setId(restauranteId);
        return produtoModelAssembler.toModel(cadastroProduto.salvar(produto));
    }
    
    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, 
                                      @RequestBody ProdutoInput produtoInput) {
        Produto produto = cadastroProduto.buscar(restauranteId, produtoId);
        produtoInputDisassembler.copyToDomainInObject(produtoInput, produto);
        return produtoModelAssembler.toModel(cadastroProduto.salvar(produto));
    }

    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        cadastroProduto.remover(restauranteId, produtoId);
    } 

}
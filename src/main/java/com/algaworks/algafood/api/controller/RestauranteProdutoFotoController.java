package com.algaworks.algafood.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.FotoProdutoInputDisassembler;
import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private CatalogoFotoProdutoService catalagoFotoProduto;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @Autowired
    private FotoProdutoInputDisassembler fotoProdutoInputDisassembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
            FotoProduto foto = fotoProdutoInputDisassembler.toDomainObject(fotoProdutoInput);
            foto.setProduto(cadastroProduto.buscar(restauranteId, produtoId));
            return  fotoProdutoModelAssembler.toModel(catalagoFotoProduto.salvar(foto, 
                fotoProdutoInput.getArquivo().getInputStream()));
    }
    

}
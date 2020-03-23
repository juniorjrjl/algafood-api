package com.algaworks.algafood.api.v1.openapi.controller;

import java.io.IOException;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {
    

     @ApiOperation("Atualiza uma Foto do Produto pelo ID do Restaurante e ID do Produto")
     @ApiResponses({
          @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
     })
    public FotoProdutoModel atualizarFoto(@ApiParam(value = "ID de um restaurante", 
                                                    example = "1",required = true)
                                          Long restauranteId, 
                                          @ApiParam(value = "ID de um produto", 
                                                    example = "1",required = true)
                                          Long produtoId,
                                          FotoProdutoInput fotoProdutoInput,
                                          @ApiParam(value = "Foto", required = true) MultipartFile arquivo) throws IOException;

    @ApiOperation(value = "Busca informações da foto", response = FotoProdutoModel.class,
                  produces = "application/json, image/jpeg, image/png")
    public FotoProdutoModel buscarFoto(@ApiParam(value = "ID de um restaurante", 
                                                 example = "1",required = true)
                                        Long restauranteId, 
                                        @ApiParam(value = "ID de um produto", 
                                                  example = "1",required = true)
                                        Long produtoId) throws HttpMediaTypeNotAcceptableException ;
    
    @ApiOperation(value = "Busca a imagem", hidden = true)
    public ResponseEntity<?> buscarArquivoFoto(@ApiParam(value = "ID de um restaurante", 
                                                         example = "1",required = true)
                                               Long restauranteId, 
                                               @ApiParam(value = "ID de um produto", 
                                                    example = "1",required = true)
                                               Long produtoId, 
                                               String acceptHeader) throws HttpMediaTypeNotAcceptableException;
    
     @ApiOperation("Exclui um produto por ID do restaurante e ID do produto")
     @ApiResponses({
          @ApiResponse(code = 404, message = "Produto não encontrado", response = Problem.class)
     })
    public void excluir(@ApiParam(value = "ID de um restaurante", 
                                  example = "1",required = true) 
                        Long restauranteId, 
                        @ApiParam(value = "ID de um produto", 
                                  example = "1",required = true)
                        Long produtoId);
    
}
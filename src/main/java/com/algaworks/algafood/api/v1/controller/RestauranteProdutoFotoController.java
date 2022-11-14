package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FotoProdutoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi{

    private final CatalogoFotoProdutoService catalagoFotoProduto;

    private final CadastroProdutoService cadastroProduto;

    private final FotoStorageService fotoStorage;

    private final FotoProdutoModelAssembler fotoProdutoModelAssembler;

    private final FotoProdutoInputDisassembler fotoProdutoInputDisassembler;

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable final Long restauranteId,
                                          @PathVariable final Long produtoId,
                                          @Valid final FotoProdutoInput fotoProdutoInput) throws IOException {
            FotoProduto foto = fotoProdutoInputDisassembler.toDomainObject(fotoProdutoInput);
            foto.setProduto(cadastroProduto.buscar(restauranteId, produtoId));
            return  fotoProdutoModelAssembler.toModel(catalagoFotoProduto.salvar(foto, 
                fotoProdutoInput.getArquivo().getInputStream()));
    }
    
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public FotoProdutoModel buscarFoto(@PathVariable final Long restauranteId,
                                       @PathVariable final Long produtoId) {
        FotoProduto fotoProduto = catalagoFotoProduto.buscar(restauranteId, produtoId);
        return fotoProdutoModelAssembler.toModel(fotoProduto);
    }
    
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> buscarArquivoFoto(@PathVariable final Long restauranteId,
                                               @PathVariable final Long produtoId,
                                               @RequestHeader(name = "accept") final String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try{
            FotoProduto fotoProduto = catalagoFotoProduto.buscar(restauranteId, produtoId);
            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
            verificarMediaType(mediaTypeFoto, mediaTypesAceitas);
            FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoProduto.getNomeArquivo());
            if (fotoRecuperada.temURL()){
                return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                    .build();
            }else{
                return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }
        }catch(EntidadeNaoEncontradaException ex){
            return ResponseEntity.notFound().build();
        }
    }
    
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable final Long restauranteId,
                        @PathVariable final Long produtoId){
        catalagoFotoProduto.excluir(restauranteId, produtoId);
    }

    private void verificarMediaType(final MediaType mediaTypeFoto,
                                    final List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
            .anyMatch(m -> m.isCompatibleWith(mediaTypeFoto));
        if (!compativel){
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
        
    }

}
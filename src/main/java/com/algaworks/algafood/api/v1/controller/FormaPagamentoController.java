package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi{

    private final CadastroFormaPagamentoService cadastroFormaPagamento;

    private final FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    
    @CheckSecurity.FormaPagamentos.PodeConsultar
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";
        Optional<OffsetDateTime> dataUltimaAtualizacao = cadastroFormaPagamento.getDataUltimaAtualizacao();
        if (dataUltimaAtualizacao.isPresent()){
            eTag = String.valueOf(dataUltimaAtualizacao.get().toEpochSecond());
        }
        if (request.checkNotModified(eTag)){
            return null;
        }

        CollectionModel<FormaPagamentoModel> formasPagamentos = 
            formaPagamentoModelAssembler.toCollectionModel(cadastroFormaPagamento.listar());
            return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formasPagamentos);
    }

    @CheckSecurity.FormaPagamentos.PodeConsultar
    @GetMapping("{idFormaPagamento}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long idFormaPagamento, ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";
        Optional<OffsetDateTime> dataUltimaAtualizacao = cadastroFormaPagamento
            .getDataUltimaAtualizacao(idFormaPagamento);
        if (dataUltimaAtualizacao.isPresent()){
            eTag = String.valueOf(dataUltimaAtualizacao.get().toEpochSecond());
        }
        if (request.checkNotModified(eTag)){
            return null;
        }
        FormaPagamentoModel formaPagamento = formaPagamentoModelAssembler
            .toModel(cadastroFormaPagamento.buscar(idFormaPagamento));
        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
            .eTag(eTag)
            .body(formaPagamento);
    }
    
    @CheckSecurity.FormaPagamentos.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamento));
    }

    @CheckSecurity.FormaPagamentos.PodeEditar
    @PutMapping("{idFormaPagamento}")
    public FormaPagamentoModel atualizar(@PathVariable Long idFormaPagamento, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscar(idFormaPagamento);
        formaPagamentoInputDisassembler.copyToDomainInObject(formaPagamentoInput, formaPagamentoAtual);
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamentoAtual));
    }

    @CheckSecurity.FormaPagamentos.PodeEditar
    @DeleteMapping("{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long idFormaPagamento){
        cadastroFormaPagamento.excluir(idFormaPagamento);
    }

}
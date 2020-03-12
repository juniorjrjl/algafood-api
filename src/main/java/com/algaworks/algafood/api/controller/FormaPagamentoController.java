package com.algaworks.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    
    @GetMapping
    public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";
        Optional<OffsetDateTime> dataUltimaAtualizacao = cadastroFormaPagamento.getDataUltimaAtualizacao();
        if (dataUltimaAtualizacao.isPresent()){
            eTag = String.valueOf(dataUltimaAtualizacao.get().toEpochSecond());
        }
        if (request.checkNotModified(eTag)){
            return null;
        }

        List<FormaPagamentoModel> formasPagamentos = 
            formaPagamentoModelAssembler.toCollectionModel(cadastroFormaPagamento.listar());
            return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formasPagamentos);
    }

    @GetMapping("{id}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long id, ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";
        Optional<OffsetDateTime> dataUltimaAtualizacao = cadastroFormaPagamento
            .getDataUltimaAtualizacao(id);
        if (dataUltimaAtualizacao.isPresent()){
            eTag = String.valueOf(dataUltimaAtualizacao.get().toEpochSecond());
        }
        if (request.checkNotModified(eTag)){
            return null;
        }
        FormaPagamentoModel formaPagamento = formaPagamentoModelAssembler
            .toModel(cadastroFormaPagamento.buscar(id));
        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
            .eTag(eTag)
            .body(formaPagamento);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamento));
    }

    @PutMapping("{id}")
    public FormaPagamentoModel atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscar(id);
        formaPagamentoInputDisassembler.copyToDomainInObject(formaPagamentoInput, formaPagamentoAtual);
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamentoAtual));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroFormaPagamento.excluir(id);
    }

}
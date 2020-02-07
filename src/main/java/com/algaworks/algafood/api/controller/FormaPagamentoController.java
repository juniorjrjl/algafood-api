package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

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
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    
    @GetMapping
    public List<FormaPagamentoModel> listar(){
        return formaPagamentoModelAssembler.toCollectionModel(cadastroFormaPagamento.listar());
    }

    @GetMapping("{id}")
    public FormaPagamentoModel buscar(@PathVariable Long id){
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamento.buscar(id));
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
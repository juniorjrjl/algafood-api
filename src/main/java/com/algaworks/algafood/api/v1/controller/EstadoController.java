package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class EstadoController implements EstadoControllerOpenApi{

    private final EstadoInputDisassembler estadoInputDisassembler;

    private final EstadoModelAssembler estadoModelAssembler;

    private final CadastroEstadoService cadastroEstado;

    @CheckSecurity.Estados.PodeConsultar
    @GetMapping
    public CollectionModel<EstadoModel> listar(){
        return estadoModelAssembler.toCollectionModel(cadastroEstado.listar());
    }

    @CheckSecurity.Estados.PodeConsultar
    @GetMapping("{idEstado}")
    public EstadoModel buscar(@PathVariable final Long idEstado){
        return estadoModelAssembler.toModel(cadastroEstado.buscar(idEstado));
    }
    
    @CheckSecurity.Estados.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid final EstadoInput estadoInput){
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
        return estadoModelAssembler.toModel(cadastroEstado.salvar(estado));
    }

    @CheckSecurity.Estados.PodeEditar
    @PutMapping("{idEstado}")
    public EstadoModel atualizar(@PathVariable final Long idEstado, @RequestBody @Valid final EstadoInput estadoInput){
        Estado estadoAtual = cadastroEstado.buscar(idEstado);
        estadoInputDisassembler.copyToDomainInObject(estadoInput, estadoAtual);
        return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoAtual));
    }

    @CheckSecurity.Estados.PodeEditar
    @DeleteMapping("{idEstado}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long idEstado){
        cadastroEstado.excluir(idEstado);
    }
    
}
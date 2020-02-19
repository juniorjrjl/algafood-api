package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;
//import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
//import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @GetMapping
    public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, 
            @PageableDefault(size = 10)Pageable pageable) {
        //pageable = traduzirPageable(pageable);
        Page<Pedido> pedidosPage = emissaoPedido.listar(filtro, pageable);
        List<Pedido> pedidos = pedidosPage.getContent();
        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler
            .toCollectionModel(pedidos);
        Page<PedidoResumoModel> pedidosModelPage = new PageImpl<PedidoResumoModel>(pedidosModel, 
                                                                                    pageable, 
                                                                                    pedidosPage.getTotalElements());
        return pedidosModelPage;

    }
    
    @GetMapping("{codigo}")
    public PedidoModel buscar(@PathVariable String codigo){
        return pedidoModelAssembler.toModel(emissaoPedido.buscar(codigo));
    }
    
    @PostMapping
    public PedidoModel emitir(@RequestBody @Valid PedidoInput pedidoInput) {
        Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
        pedido.getCliente().setId(1L);//provisório até implementar autenticação
        emissaoPedido.salvar(pedido);
        return pedidoModelAssembler.toModel(pedido);
    }
    
    /*private Pageable traduzirPageable(Pageable pageable){
        var mapeamento = ImmutableMap.of(
            "codigo", "codigo",
            "restaurante.nome", "restaurante.nome",
            "nomeCliente", "cliente.nome",
            "valorTotal", "valorTotal"
        );
        return PageableTranslator.translate(pageable, mapeamento);
    }*/

}
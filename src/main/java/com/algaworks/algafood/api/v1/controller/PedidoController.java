package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;



@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PedidoController implements PedidoControllerOpenApi{

    private final EmissaoPedidoService emissaoPedido;

    private final PedidoModelAssembler pedidoModelAssembler;

    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;

    private final PedidoInputDisassembler pedidoInputDisassembler;

    private final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    private final AlgaSecurity algaSecurity;
    
    @CheckSecurity.Pedidos.PodeConsultar
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(final PedidoFilter filtro, @PageableDefault final Pageable pageable) {
        Pageable pageableTraduzido  = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = emissaoPedido.listar(filtro, pageableTraduzido);
        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);

    }
    
    @CheckSecurity.Pedidos.PodeBuscar
    @GetMapping("{codigoPedido}")
    public PedidoModel buscar(@PathVariable final String codigoPedido){
        return pedidoModelAssembler.toModel(emissaoPedido.buscar(codigoPedido));
    }
    
    @CheckSecurity.Pedidos.PodeCriar
    @PostMapping
    public PedidoModel emitir(@RequestBody @Valid final PedidoInput pedidoInput) {
        Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
        pedido.getCliente().setId(algaSecurity.getUsuarioId());
        emissaoPedido.salvar(pedido);
        return pedidoModelAssembler.toModel(pedido);
    }
    
    private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = Map.of(
				"codigo", "codigo",
				"subtotal", "subtotal",
				"taxaFrete", "taxaFrete",
				"valorTotal", "valorTotal",
				"dataCriacao", "dataCriacao",
				"nomeRestaurante", "restaurante.nome",
				"restauranteId", "restaurante.id",
				"clienteId", "cliente.id",
				"nomeCliente", "cliente.nome"
			);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}

}
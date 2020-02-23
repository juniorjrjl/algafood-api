package com.algaworks.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Pedido_;
import com.algaworks.algafood.domain.model.Restaurante_;

import org.springframework.data.jpa.domain.Specification;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){
        return (root, query, builder) ->{
            if (Pedido.class.equals(query.getResultType())){
                root.fetch(Pedido_.restaurante).fetch(Restaurante_.cozinha);
                root.fetch(Pedido_.cliente);
            }
            var predicates = new ArrayList<Predicate>();
            if (filtro.getClienteId() != null){
                predicates.add(builder.equal(root.get(Pedido_.cliente), filtro.getClienteId()));
            }
            if (filtro.getRestauranteId() != null){
                predicates.add(builder.equal(root.get(Pedido_.restaurante), filtro.getClienteId()));
            }
            if (filtro.getDataCriacaoInicio() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get(Pedido_.dataCriacao), filtro.getDataCriacaoInicio()));
            }
            if (filtro.getDataCriacaoFim() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get(Pedido_.dataCriacao), filtro.getDataCriacaoFim()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    
}
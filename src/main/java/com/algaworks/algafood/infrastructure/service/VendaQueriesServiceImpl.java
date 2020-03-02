package com.algaworks.algafood.infrastructure.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Pedido_;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueriesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class VendaQueriesServiceImpl implements VendaQueriesService {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		var builder = entityManager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root  = query.from(Pedido.class);

		var functionConvertTzDataCriacao = builder.function("convert_tz", Date.class, root.get(Pedido_.dataCriacao), 
			                                                builder.literal("+00:00"), builder.literal(timeOffset));
		var functionDateDataCricao = builder.function("date", Date.class, functionConvertTzDataCriacao);
		var selection = builder.construct(VendaDiaria.class, 
			functionDateDataCricao,
			builder.count(root.get(Pedido_.id)),
			builder.sum(root.get(Pedido_.valorTotal)));

		var predicates = new ArrayList<Predicate>();
		predicates.add(root.get(Pedido_.status).in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		if (filtro.getDataCriacaoInicio() != null){
			predicates.add(builder.greaterThanOrEqualTo(root.get(Pedido_.dataCriacao), filtro.getDataCriacaoInicio()));
		}
		
		if (filtro.getDataCriacaoFim() != null){
			predicates.add(builder.lessThanOrEqualTo(root.get(Pedido_.dataCriacao), filtro.getDataCriacaoFim()));
		}

		if (filtro.getRestauranteId()!= null){
			predicates.add(builder.equal(root.get(Pedido_.id), filtro.getRestauranteId()));
		}

		query.select(selection);
		query.groupBy(functionDateDataCricao);
		query.where(predicates.toArray(new Predicate[predicates.size()]));
		return entityManager.createQuery(query).getResultList();
	}

    
}
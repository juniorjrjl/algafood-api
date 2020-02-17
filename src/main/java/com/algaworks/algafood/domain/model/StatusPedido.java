package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPedido {

	CRIADO("criado", new ArrayList<>()),
	CONFIRMADO("confirmado", Arrays.asList(CRIADO)),
	ENTREGUE("entregue", Arrays.asList(CONFIRMADO)),
	CANCELADO("cancelado", Arrays.asList(CRIADO));

	private String descricao;
	private List<StatusPedido> statusAnteriores;

	public boolean naoPodeAlterarPara(StatusPedido status){
		return !status.statusAnteriores.contains(this);
	}

}
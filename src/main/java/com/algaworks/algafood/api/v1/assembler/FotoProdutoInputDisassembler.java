package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoInputDisassembler {

    @Autowired
	private ModelMapper modelMapper;
	
	public FotoProduto toDomainObject(FotoProdutoInput fotoProdutoInput) {
		return modelMapper.map(fotoProdutoInput, FotoProduto.class);
	}
	
	public void copyToDomainInObject(FotoProdutoInput fotoProdutoInput, FotoProduto fotoProduto){
		modelMapper.map(fotoProdutoInput, fotoProduto);
	}

}
package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CidadeInputDisassemblerV2 {

	private final ModelMapper modelMapper;
	
	public Cidade toDomainObject(final CidadeInputV2 cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToDomainInObject(final CidadeInputV2 cidadeInput, final Cidade cidade){
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeInput, cidade);
	}

}
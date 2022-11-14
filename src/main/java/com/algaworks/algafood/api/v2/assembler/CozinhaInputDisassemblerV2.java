package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algafood.domain.model.Cozinha;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CozinhaInputDisassemblerV2 {

	private final ModelMapper modelMapper;
	
	public Cozinha toDomainObject(final CozinhaInputV2 CozinhaInputV2) {
		return modelMapper.map(CozinhaInputV2, Cozinha.class);
	}
	
	public void copyToDomainInObject(final CozinhaInputV2 CozinhaInputV2, final Cozinha cozinha){
		modelMapper.map(CozinhaInputV2, cozinha);
	}

}
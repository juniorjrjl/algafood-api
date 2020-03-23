package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.input.SenhaUsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioAtualizacaoInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioCadastroInput;
import com.algaworks.algafood.domain.model.Usuario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisassembler {

    @Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioCadastroInput usuarioCadastroInput) {
		return modelMapper.map(usuarioCadastroInput, Usuario.class);
	}
    
    public Usuario toDomainObject(UsuarioAtualizacaoInput usuarioAtualizacaoInput) {
		return modelMapper.map(usuarioAtualizacaoInput, Usuario.class);
	}
    
	public void copyToDomainInObject(UsuarioCadastroInput usuarioCadastroInput, Usuario usuario){
		modelMapper.map(usuarioCadastroInput, usuario);
	}
    
    public void copyToDomainInObject(UsuarioAtualizacaoInput usuarioAtualizacaoInput, Usuario usuario){
		modelMapper.map(usuarioAtualizacaoInput, usuario);
	}

    public void copyToDomainInObject(SenhaUsuarioInput senhaUsuarioInput, Usuario usuario){
		modelMapper.map(senhaUsuarioInput, usuario);
	}

}
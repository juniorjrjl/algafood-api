package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CadastroCozinhaIntegrationIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	//@Test
	public void testarCadastroCozinhaComSucesso() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Chinesa");
		Cozinha novaCozinha = cadastroCozinha.salvar(cozinha);
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	//@Test
	public void testarCadastrarCozinhaQuandoSemNome(){
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);
		assertThrows(ConstraintViolationException.class, () -> cadastroCozinha.salvar(cozinha));
	}

	//@Test
	public void testarExclusaoCozinhaEmUso(){
		assertThrows(EntidadeEmUsoException.class, () -> cadastroCozinha.excluir(1L));
	}

	////@Test
	public void testarExclusaoCozinhaInexistente(){
		assertThrows(CozinhaNaoEncontradaException.class, () -> cadastroCozinha.excluir(10000L));
	}

}

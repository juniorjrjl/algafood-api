package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTest {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void testarCadastroCozinhaComSucesso() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Chinesa");
		Cozinha novaCozinha = cadastroCozinha.salvar(cozinha);
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test(expected = ConstraintViolationException.class)
	public void testarCadastrarCozinhaQuandoSemNome(){
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);
		cadastroCozinha.salvar(cozinha);
	}

}

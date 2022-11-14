package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("/application-test.yml")
public class CadastroRestauranteAPITest {

    private static final int ID_INEXISTENTE = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databasecleaner;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private List<Restaurante> restaurantes;
    
    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";
        databasecleaner.clearTables();
        restaurantes= prepararDados();
    }

    //@Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas(){
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }
    
    //@Test
    public void verificarCozinhas_QuandoConsultarCozinhas(){
        List<String> nomeRestaurantes = restaurantes.stream()
            .map(c -> c.getNome()).collect(Collectors.toList());
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", Matchers.hasSize(restaurantes.size()))
            .body("nome", Matchers.hasItems(nomeRestaurantes.toArray(new String[nomeRestaurantes.size()])));
    }

    //@Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha(){
        given()
            .body(ResourceUtils.getContentFromResource("/json/restaurante/post.json"))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    //@Test
    public void deveRetornarRespostaStatusCorretos_QuandoConsultarCozinhaExistente(){
        given()
            .pathParam("id", restaurantes.get(1).getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }


    //@Test
    public void deveRetornarRespostaStatusCorretos_QuandoConsultarCozinhaInexistente(){
        given()
            .pathParam("id", ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private List<Restaurante> prepararDados(){
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");
        cozinhaRepository.save(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Americana");
        cozinhaRepository.save(cozinha2);

        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("restaurante do jão");
        restaurante1.setCozinha(cozinha1);
        restaurante1.setTaxaFrete(BigDecimal.ZERO);
        restauranteRepository.save(restaurante1);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("restaurante da maria");
        restaurante2.setCozinha(cozinha2);
        restaurante2.setTaxaFrete(BigDecimal.ZERO);
        restauranteRepository.save(restaurante2);
        return List.of(restaurante1, restaurante2);
    }

}
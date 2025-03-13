package com.vemser.rest.tests.produto;

import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.request.produto.ProdutosRequest;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CadastrarProdutoTest extends BaseAuth {

    private final ProdutosClient produtosClient = new ProdutosClient();
    private  String idValido;
    @Test
    void testValidarContratoCadastroDeProduto() {
        ProdutosRequest produto = ProdutosDataFactory.produtoValido();

        idValido = produtosClient.cadastrarProduto(produto, token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body(matchesJsonSchemaInClasspath("schemas/produtos/produtos_por_cadastro.json"))
                .extract().path("_id");
        produtosClient.excluirProduto(idValido, token)
        ;
    }

    @Test
    void testCadastrarProdutoComSucesso() {
        ProdutosRequest produto = ProdutosDataFactory.produtoValido();

        idValido =produtosClient.cadastrarProduto(produto, token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue())
                .extract().path("_id");
        produtosClient.excluirProduto(idValido, token)
        ;
    }

    @ParameterizedTest
    @MethodSource("com.vemser.rest.data.provider.ProdutosDataProvider#produtoComCamposEmBranco")
    void testTentarCadastrarProdutosComCamposVazios(ProdutosRequest produto, String key, String value){

        produtosClient.cadastrarProduto(produto, token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(key, Matchers.equalTo(value))
        ;
    }
    @ParameterizedTest
    @MethodSource("com.vemser.rest.data.provider.ProdutosDataProvider#produtoComCamposInvalidos")
    void testTentarCadastrarProdutosComCamposInvalidos(ProdutosRequest produto, String key, String value){

        produtosClient.cadastrarProduto(produto, token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(key, Matchers.equalTo(value))
        ;
    }
}


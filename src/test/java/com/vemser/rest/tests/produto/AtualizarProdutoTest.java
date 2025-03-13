package com.vemser.rest.tests.produto;

import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.request.produto.ProdutosRequest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AtualizarProdutoTest extends BaseAuth {

    private final ProdutosClient produtosClient = new ProdutosClient();

    private String idValido;

    @BeforeEach
    void setUp() {

        ProdutosRequest produto = ProdutosDataFactory.produtoValido();

        idValido = produtosClient.cadastrarProduto(produto, token)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                    .extract().path("_id")
            ;
    }

    @AfterEach
    void tearDown() {
        produtosClient.excluirProduto(idValido, token);
    }

    @Test
    void testAtualizarProdutoComSucessoAssertions() {
        ProdutosRequest produtoAtualizado = ProdutosDataFactory.produtoValido();

        Response response = produtosClient.atualizarProduto(idValido, produtoAtualizado, token);

        response
            .then()
                .statusCode(HttpStatus.SC_OK)
            ;

        String message = response.jsonPath().getString("message");
        assertEquals("Registro alterado com sucesso", message);
    }

    @Test
    void testTentarAtualizarProdutoPreenchendoQuantidadeComNumeroNegativo() {
        ProdutosRequest produtoAtualizado = ProdutosDataFactory.produtoComQuantidadeNegativa();

        Response response = produtosClient.atualizarProduto(idValido, produtoAtualizado, token);

        response
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract().response()
            ;

        String quantidadeError = response.jsonPath().getString("quantidade");
        assertEquals("quantidade deve ser maior ou igual a 0", quantidadeError);
    }

}

package com.vemser.rest.tests.produto;

import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.request.produto.ProdutosRequest;
import com.vemser.rest.response.produto.ProdutoResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BuscarProdutoPorIDTest extends BaseAuth {

    private final ProdutosClient produtosClient = new ProdutosClient();
    private  String idValido;
    private final String idInvalido = ProdutosDataFactory.gerarStringAleatorio();


    @Test
    void testValidarContratoProdutosPorIDComSucesso() {
        ProdutosRequest produto = ProdutosDataFactory.produtoValido();
        idValido = produtosClient.cadastrarProduto(produto, token)
            .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract().path("_id");

        Response response = produtosClient.buscarProdutoPorId(idValido);
        response
            .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/produtos/produtos_por_id.json"));
        produtosClient.excluirProduto(idValido, token)
        ;
    }

    @Test
    void testBuscarProdutoPorIDComSucesso() {
        ProdutosRequest produto = ProdutosDataFactory.produtoValido();
        idValido = produtosClient.cadastrarProduto(produto, token)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().path("_id");
        Response response = produtosClient.buscarProdutoPorId(idValido);

        ProdutoResponse produtoResponse = response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ProdutoResponse.class);

        Assertions.assertAll("response",
                () -> Assertions.assertEquals(produto.getNome(), produtoResponse.getNome()),
                () -> Assertions.assertEquals(produto.getPreco(), produtoResponse.getPreco()),
                () -> Assertions.assertTrue(produto.getDescricao().contains(produtoResponse.getDescricao())),
                () -> Assertions.assertNotNull(produtoResponse.getQuantidade())
        )
        ;
        produtosClient.excluirProduto(idValido, token);
    }

    @Test
    void testTentarBuscarProdutoPorIDInvalido() {
        Response response = produtosClient.buscarProdutoPorId(idInvalido);

        response
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
        String message = response.jsonPath().getString("message");
        assertEquals("Produto não encontrado", message)
        ;
    }
}

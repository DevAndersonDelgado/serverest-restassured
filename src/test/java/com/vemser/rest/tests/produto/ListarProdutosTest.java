package com.vemser.rest.tests.produto;

import com.vemser.rest.client.ProdutosClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class ListarProdutosTest {

    private final ProdutosClient produtosClient = new ProdutosClient();

    @Test
    void testDeveValidarContratoListaDeProdutosComSucesso() {
        produtosClient.listarProdutos()
            .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/produtos/produtos_por_lista.json"));
    }

    @Test
    void testListarTodosProdutosComSucesso() {

        produtosClient.listarProdutos()
            .then()
                .statusCode(HttpStatus.SC_OK);
    }
}

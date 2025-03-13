package com.vemser.rest.tests.carrinho;

import com.vemser.rest.client.CarrinhoClient;
import com.vemser.rest.tests.produto.BaseAuth;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class ListarCarrinhoTest extends BaseAuth {

CarrinhoClient carrinhoClient = new CarrinhoClient();


    @Test
    void testValidarListaCarrinhoComSucesso() {
        carrinhoClient.listarCarrinhos(token)
            .then()
                .statusCode(HttpStatus.SC_OK)
            ;
    }

    @Test
    void testValidarContratoListarCarrinhoComSucesso() {
        carrinhoClient.listarCarrinhos(token)
            .then()
                .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/carrinhos/carrinhos_por_lista.json"))
            ;
    }
}


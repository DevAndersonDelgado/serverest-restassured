package com.vemser.rest.tests.produto;

import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ExcluirProdutoTest extends BaseAuth {

    private final ProdutosClient produtosClient = new ProdutosClient();
    private String idInvalido = ProdutosDataFactory.gerarStringAleatorio();


    @Test
    void testExcluirProdutoComSucesso() {
        Response produtoResponse = produtosClient.cadastrarProduto(ProdutosDataFactory.produtoValido(), token);

        String produtoId = produtoResponse.then()
                .extract()
                .path("_id");

        Response response = produtosClient.excluirProduto(produtoId, token);

        assertAll("Validando a resposta de exclusão",
                () -> response.then().header("Content-Type", equalTo("application/json; charset=utf-8")),
                () -> response.then().statusCode(HttpStatus.SC_OK),
                () -> response.then().body("message", equalTo("Registro excluído com sucesso"))
        );
    }

    @Test
    void testTentarExcluirProdutoComIDInvalido() {
        Response response = produtosClient.excluirProduto(idInvalido, token);

        assertAll("Validando a resposta de exclusão com ID inválido",
                () -> response.then().header("Content-Type", equalTo("application/json; charset=utf-8")),
                () -> response.then().statusCode(HttpStatus.SC_OK),
                () -> response.then().body("message", equalTo("Nenhum registro excluído"))
        );
    }
}

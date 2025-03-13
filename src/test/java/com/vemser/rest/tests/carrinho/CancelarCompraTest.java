package com.vemser.rest.tests.carrinho;

import com.vemser.rest.client.CarrinhoClient;
import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.request.carrinho.CarrinhoProdutoRequest;
import com.vemser.rest.request.carrinho.CarrinhosRequest;
import com.vemser.rest.request.produto.ProdutosRequest;
import com.vemser.rest.tests.produto.BaseAuth;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CancelarCompraTest extends BaseAuth {
    private final CarrinhoClient carrinhoClient = new CarrinhoClient();
    private final ProdutosClient produtosClient = new ProdutosClient();
    private final CarrinhosRequest carrinhosRequest = new CarrinhosRequest();

    @BeforeEach
    void criarProdutos() {
        List<CarrinhoProdutoRequest> carrinhoProdutosRequest = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ProdutosRequest produtosRequest = ProdutosDataFactory.produtoValido();
            String produtoId = produtosClient.cadastrarProduto(produtosRequest, token)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                        .extract()
                            .path("_id")
                ;

            Response produtoResponse = produtosClient.buscarProdutoPorId(produtoId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                        .extract()
                            .response()
                ;
            CarrinhoProdutoRequest carrinhoProdutoRequest = new CarrinhoProdutoRequest(
                    produtoResponse.jsonPath().getString("_id"),
                    produtoResponse.jsonPath().getLong("quantidade")
            );
            carrinhoProdutosRequest.add(carrinhoProdutoRequest);
        }
        carrinhosRequest.setProdutos(carrinhoProdutosRequest);
        carrinhoClient.cadastrarCarrinho(carrinhosRequest, token)
            .then()
                 .statusCode(HttpStatus.SC_CREATED)
            ;
    }

    @AfterEach
    void excluirProdutos(){
        carrinhosRequest.getProdutos().forEach(carrinhoProdutoRequest -> produtosClient.excluirProduto(carrinhoProdutoRequest.getIdProduto(), token));
    }

    @Test
    void testValidarContratoCancelarCompraComSucesso() {
        carrinhoClient.cancelarCompra(token)
            .then()
                .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/carrinhos/cancelar_compra.json"))
            ;
    }

    @Test
    void testValidarCancelarCompraComSucesso() {
        carrinhoClient.cancelarCompra(token)
            .then()
                .statusCode(HttpStatus.SC_OK)
            ;
    }


}

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

public class ConcluirCompraTest extends BaseAuth {
    private final CarrinhoClient carrinhosClient = new CarrinhoClient();
    private final ProdutosClient produtosClient = new ProdutosClient();
    private final CarrinhosRequest carrinhoModel = new CarrinhosRequest();

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
        carrinhoModel.setProdutos(carrinhoProdutosRequest);
        carrinhosClient.cadastrarCarrinho(carrinhoModel, token)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
            ;
    }

    @AfterEach
    void excluirProdutos(){
        carrinhoModel.getProdutos().forEach(carrinhoProdutoRequest -> produtosClient.excluirProduto(carrinhoProdutoRequest.getIdProduto(), token));
    }

    @Test
    void testValidarConcluirCompraComSucesso() {
        carrinhosClient.concluirCompra(token)
            .then()
                .statusCode(HttpStatus.SC_OK)
            ;
    }

    @Test
    void testValidarContratoConcluirCompraComSucesso() {
        carrinhosClient.concluirCompra(token)
            .then()
                .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/carrinhos/concluir_compra.json"))
            ;
    }
}


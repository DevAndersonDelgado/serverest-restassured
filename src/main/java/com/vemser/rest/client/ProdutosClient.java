package com.vemser.rest.client;

import com.vemser.rest.request.produto.ProdutosRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;


public class ProdutosClient extends BaseClient{
    private final String PRODUTOS = "/produtos";

    public Response cadastrarProduto(ProdutosRequest produto, String token) {
        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .contentType(io.restassured.http.ContentType.JSON)
                        .body(produto)
                .when()
                        .post("/produtos");
    }

    public Response atualizarProduto(String id, ProdutosRequest produto, String token) {
        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .contentType(ContentType.JSON)
                        .body(produto)
                        .pathParam("id", id)
                .when()
                        .put(PRODUTOS + "/{id}");
    }
    public Response buscarProdutoPorId(String id) {
        return
                given()
                        .spec(super.set())
                        .pathParam("_id", id)
                .when()
                        .get(PRODUTOS + "/{_id}");
    }
    public Response excluirProduto(String id, String token) {
        return
                given()
                       .spec(super.set())
                       .auth().oauth2(token)
                       .pathParam("id", id)
                .when()
                       .delete(PRODUTOS + "/{id}");
    }

    public Response listarProdutos() {
        return
                given()
                        .spec(super.set())
                .when()
                        .get(PRODUTOS);
    }
}


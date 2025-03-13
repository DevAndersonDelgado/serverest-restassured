package com.vemser.rest.client;

import com.vemser.rest.request.carrinho.CarrinhosRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class CarrinhoClient extends BaseClient {
    private final String CARRINHOS = "/carrinhos";

    public Response listarCarrinhos(String token) {
        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .when()
                        .get(CARRINHOS)
                ;
    }

    public Response cadastrarCarrinho(CarrinhosRequest carrinho, String token) {
        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .contentType(ContentType.JSON)
                        .body(carrinho.toJson())
                        .when()
                        .post(CARRINHOS)
                ;
    }

    public Response buscarCarrinho(String carrinhoId, String token) {
        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .pathParam("id", carrinhoId)
                        .when()
                        .get(CARRINHOS + "/{id}")
                ;
    }

    public Response concluirCompra(String token) {
        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .when()
                        .delete(CARRINHOS + "/concluir-compra")
                ;
    }

    public Response cancelarCompra(String token) {
        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .when()
                        .delete(CARRINHOS + "/cancelar-compra")
                ;
    }
}

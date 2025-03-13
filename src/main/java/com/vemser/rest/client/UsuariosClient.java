package com.vemser.rest.client;

import com.vemser.rest.request.usuario.UsuarioRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class UsuariosClient extends BaseClient {

    private final String USUARIOS = "/usuarios";

    public Response cadastrarUsuarios(UsuarioRequest usuario) {

        return
                given()
                        .spec(super.set())
                        .contentType(ContentType.JSON)
                        .body(usuario)
                .when()
                        .post(USUARIOS)
                ;
    }

    public Response listarUsuarios() {
        return
                given()
                        .spec(super.set())
                .when()
                        .get(USUARIOS)
                ;
    }

    public Response excluirUsuario(String id) {
        return
                given()
                        .spec(super.set())
                        .pathParam("id", id)
                .when()
                        .delete(USUARIOS + "/{id}")
                ;
    }

    public Response buscarUsuarioPorId(String id) {
        return
                given()
                        .spec(super.set())
                        .pathParam("id", id)
                .when()
                        .get(USUARIOS + "/{id}")
                ;
    }

    public Response buscarUsuarioPorNome(String nome) {
        return 
                given()
                        .spec(super.set())
                        .queryParam("nome", nome)
                .when()
                        .get(USUARIOS);
    }
    public Response atualizarUsuario(String id, UsuarioRequest usuario) {
        return
                given()
                        .spec(super.set())
                        .contentType(ContentType.JSON)
                        .body(usuario)
                        .pathParam("id", id)
                .when()
                        .put(USUARIOS + "/{id}")
                ;
    }
}













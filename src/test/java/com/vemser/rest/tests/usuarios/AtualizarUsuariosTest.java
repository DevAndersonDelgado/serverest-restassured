package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.request.usuario.UsuarioRequest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AtualizarUsuariosTest {

    private final UsuariosClient usuariosClient = new UsuariosClient();
    String idValido;

    @BeforeEach
    void setUp() {

        UsuarioRequest usuario = UsuariosDataFactory.usuarioValido();

        idValido = usuariosClient.cadastrarUsuarios(usuario)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().path("_id");
    }

    @AfterEach
    void tearDown() {
        usuariosClient.excluirUsuario(idValido);
    }
    @Test
    void testAtualizarUsuariosComSucesso() {
        UsuarioRequest usuarioAtualizado = UsuariosDataFactory.usuarioValido();

        Response response = usuariosClient.atualizarUsuario(idValido, usuarioAtualizado);

        response
            .then()
                .statusCode(HttpStatus.SC_OK);

        String message = response.jsonPath().getString("message");
        assertEquals("Registro alterado com sucesso", message);
    }

    @Test
    void testTentarAtualizarUsuariosSemPreencherSenha(){

        UsuarioRequest usuarioAtualizado = UsuariosDataFactory.usuarioComPasswordEmBranco();

        Response response = usuariosClient.atualizarUsuario(idValido, usuarioAtualizado);

        response
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().response();

        String passwordError = response.jsonPath().getString("password");
        assertEquals("password não pode ficar em branco", passwordError)
        ;
    }
}

package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.request.usuario.UsuarioRequest;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class CadastrarUsuariosTest {

    private final UsuariosClient usuariosClient = new UsuariosClient();

    @Test
    void testValidarContratoCadastroDeUsuario() {
        UsuarioRequest usuario = UsuariosDataFactory.usuarioValido();

        String usuarioId = usuariosClient.cadastrarUsuarios(usuario)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body(matchesJsonSchemaInClasspath("schemas/usuarios/usuarios_por_cadastro.json"))
                .extract().path("_id")
            ;
        usuariosClient.excluirUsuario(usuarioId);
    }
    @Test
    void testCadastrarUsuarioComSucesso(){
        UsuarioRequest usuario = UsuariosDataFactory.usuarioValido();

        String usuarioId = usuariosClient.cadastrarUsuarios(usuario)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                    .body("message", equalTo("Cadastro realizado com sucesso"))
                    .body("_id", notNullValue())
                        .extract().path("_id")
            ;
        usuariosClient.excluirUsuario(usuarioId);
    }
    @Test
    void testTentarCadastrarUsuarioSemPreencherSenha(){
        UsuarioRequest usuario = UsuariosDataFactory.usuarioComPasswordEmBranco();

        usuariosClient.cadastrarUsuarios(usuario)
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @ParameterizedTest
    @MethodSource("com.vemser.rest.data.provider.UsuariosDataProvider#usuarioDataProvider")
    void testTentarCadastrarUsuariosComCamposEmBranco(UsuarioRequest usuario, String key, String value){

        usuariosClient.cadastrarUsuarios(usuario)
            .then()
                .statusCode(400)
                    .body(key, Matchers.equalTo(value))
            ;
    }
}


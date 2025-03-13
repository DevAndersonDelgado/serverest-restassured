package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.request.usuario.UsuarioRequest;
import com.vemser.rest.response.usuario.UsuarioResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

class BuscarUsuariosPorIDTest {

    private final UsuariosClient usuariosClient = new UsuariosClient();
    String idValido;
    String idInvalido = ProdutosDataFactory.gerarStringAleatorio();

    @Test
    void testDeveValidarContratoUsuariosPorIDComSucesso() {
        UsuarioRequest usuario = UsuariosDataFactory.usuarioValido();
        idValido = usuariosClient.cadastrarUsuarios(usuario)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().path("_id");
        Response response = usuariosClient.buscarUsuarioPorId(idValido);
        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/usuarios/usuarios_por_id.json"))
        ;
        usuariosClient.excluirUsuario(idValido);
    }

    @Test
    void testBuscarUsuarioPorIDComHamcrest() {
        UsuarioRequest usuario = UsuariosDataFactory.usuarioValido();
        idValido = usuariosClient.cadastrarUsuarios(usuario)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().path("_id");
        Response response = usuariosClient.buscarUsuarioPorId(idValido);

        response
            .then()
                .header("Content-Type", "application/json; charset=utf-8") //cabeçalho
                .statusCode(200)
                .time(lessThan(3000L)) //tempo menor que 3 milisegundos
                .body("_id", notNullValue()) //importado biblioteca matcher, dizendo que o valor não pode ser nulo
                .body("nome", equalTo(usuario.getNome())) //verifica o nome se é igual
                .body("nome", containsString(usuario.getNome())) //verifica se uma parte do nome é igual
                .body("email", containsStringIgnoringCase(usuario.getEmail())) //verifica se o nome é igual mesmo ele sendo maiusculo e minusculo
        ;
        usuariosClient.excluirUsuario(idValido);
    }

    @Test
    void testBuscarUsuarioPorIDComAssertions() {
        UsuarioRequest usuario = UsuariosDataFactory.usuarioValido();
        idValido = usuariosClient.cadastrarUsuarios(usuario)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().path("_id");
        Response response = usuariosClient.buscarUsuarioPorId(idValido);

        UsuarioResponse usuarioResponse = response
            .then()
                .statusCode(200)
                .extract()
                .as(UsuarioResponse.class);

        Assertions.assertEquals(usuario.getNome(), usuarioResponse.getNome());
        Assertions.assertEquals(usuario.getEmail(), usuarioResponse.getEmail());
        usuariosClient.excluirUsuario(idValido);
    }

    @Test
    void testBuscarUsuarioPorIDComAssertALL() {
        UsuarioRequest usuario = UsuariosDataFactory.usuarioValido();
        idValido = usuariosClient.cadastrarUsuarios(usuario)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().path("_id");
        Response response = usuariosClient.buscarUsuarioPorId(idValido);

        UsuarioResponse usuarioResponse = response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(UsuarioResponse.class);

        Assertions.assertAll("response",
                () -> Assertions.assertEquals(usuario.getNome(), usuarioResponse.getNome()),
                () -> Assertions.assertEquals(usuario.getEmail(), usuarioResponse.getEmail()),
                () -> Assertions.assertTrue(usuarioResponse.getEmail().contains(usuario.getEmail())),
                () -> Assertions.assertNotNull(usuarioResponse.getPassword())
        )
        ;
        usuariosClient.excluirUsuario(idValido);
    }

    @Test
    void testTentarBuscarUsuarioPorIdInvalido() {

        Response response = usuariosClient.buscarUsuarioPorId(idInvalido);

        response
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Usuário não encontrado"));
    }
}


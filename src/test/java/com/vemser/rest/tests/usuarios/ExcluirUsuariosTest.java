package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

class ExcluirUsuariosTest {

    private final UsuariosClient usuariosClient = new UsuariosClient();
    private String idInvalido = ProdutosDataFactory.gerarStringAleatorio();
    @Test
    void testExcluirUsuarioComSucesso() {

        Response usuarioResponse = usuariosClient.cadastrarUsuarios(UsuariosDataFactory.usuarioValido());

        String usuarioId = usuarioResponse.then()
                .extract()
                .path("_id");

        Response response = usuariosClient.excluirUsuario(usuarioId);

        assertAll("Validando a resposta de exclusão",
                () -> response.then().header("Content-Type", equalTo("application/json; charset=utf-8")),
                () -> response.then().statusCode(HttpStatus.SC_OK),
                () -> response.then().body("message", equalTo("Registro excluído com sucesso"))
        );
    }

    @Test
    void testTentarExcluirUsuarioComIDInvalido() {

        Response response = usuariosClient.excluirUsuario(idInvalido);

        assertAll("Validando a resposta",
                () -> response.then().header("Content-Type", equalTo("application/json; charset=utf-8")),
                () -> response.then().statusCode(HttpStatus.SC_OK),
                () -> response.then().body("message", equalTo("Nenhum registro excluído"))
        );
    }
}

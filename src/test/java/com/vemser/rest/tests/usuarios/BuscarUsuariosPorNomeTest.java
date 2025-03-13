package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.request.usuario.UsuarioRequest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.hasSize;

class BuscarUsuariosPorNomeTest {

    private final UsuariosClient usuariosClient = new UsuariosClient();

    @Test
    void testBuscarUsuariosPorNomeComSucesso() {
        UsuarioRequest usuario = UsuariosDataFactory.usuarioValido();
        String usuarioId = usuariosClient.cadastrarUsuarios(usuario)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().path("_id");
        Response response = usuariosClient.buscarUsuarioPorNome(usuario.getNome());
        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .body("usuarios", hasSize(1));
        usuariosClient.excluirUsuario(usuarioId);
    }

    @Test
    void testTentarBuscarUsuarioPorNomeInvalido() {

        Response response = usuariosClient.buscarUsuarioPorNome(UsuariosDataFactory.gerarStringAleatorio());
                 response
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("usuarios", hasSize(0));
    }
}



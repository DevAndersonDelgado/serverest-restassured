package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

class ListarUsuariosTest {

    private final UsuariosClient usuariosClient = new UsuariosClient();

    @Test
    void testDeveValidarContratoListaDeUsuariosComSucesso() {
        usuariosClient.listarUsuarios()
            .then()
                .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/usuarios/usuarios_por_lista.json"));
    }
    @Test
    void testListarTodosUsuariosComSucesso() {

         usuariosClient.listarUsuarios()
            .then()
                        .statusCode(HttpStatus.SC_OK);
    }
}

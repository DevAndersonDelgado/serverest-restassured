package com.vemser.rest.tests.produto;

import com.vemser.rest.client.AuthClient;
import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.request.login.LoginRequest;
import com.vemser.rest.response.usuario.UsuarioResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseAuth {

    private final UsuariosClient usuariosClient = new UsuariosClient();
    private final AuthClient authClient = new AuthClient();
    protected String token;
    protected UsuarioResponse usuarioAdmin;

    @BeforeEach
    void setUpGlobal(){
        String usuarioID = usuariosClient.cadastrarUsuarios(UsuariosDataFactory.usuarioAdministrador())
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().path("_id");
        usuarioAdmin = usuariosClient.buscarUsuarioPorId(usuarioID)
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(UsuarioResponse.class);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(usuarioAdmin.getEmail());
        loginRequest.setPassword(usuarioAdmin.getPassword());
        token = authClient.login(loginRequest)
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getString("authorization").substring(7);
    }
    @AfterEach
    void tearDownGlobal(){
        usuariosClient.excluirUsuario(usuarioAdmin.getId());
    }
}

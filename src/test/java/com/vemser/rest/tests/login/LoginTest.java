package com.vemser.rest.tests.login;

import com.vemser.rest.client.AuthClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.request.login.LoginRequest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

class LoginTest {
    LoginRequest loginRequest;
    private AuthClient authClient;

    @BeforeEach
    public void setUp() {
        authClient = new AuthClient();
    }

    @Test
    void testLogarUsuarioComSucesso() {
        loginRequest = LoginDataFactory.login();

        Response response = authClient.login(loginRequest);

        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .body("message", equalTo("Login realizado com sucesso"))
                .body("authorization", notNullValue())
        ;
    }

    @Test
    void testTentarLogarUsuarioComSenhaInvalida() {
        loginRequest= LoginDataFactory.loginSenhaInvalida();

        Response response = authClient.login(loginRequest);

        response
            .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("message", equalTo("Email e/ou senha inválidos"))
                .body("authorization", nullValue())
        ;
    }

    @Test
    void testTentarLogarUsuarioComEmailInvalido() {
        loginRequest = LoginDataFactory.loginEmailInvalido();

        Response response = authClient.login(loginRequest);

        response
            .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("message", equalTo("Email e/ou senha inválidos"))
                .body("authorization", nullValue())
        ;
    }


    @Test
    void testTentarLogarUsuarioComCamposVazios() {
        loginRequest = LoginDataFactory.loginCamposVazios();

        Response response = authClient.login(loginRequest);

        response
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("email", equalTo("email não pode ficar em branco"))
                .body("password", equalTo("password não pode ficar em branco"))
                .body("authorization", nullValue())
        ;
    }
}


package com.vemser.rest.client;

import com.vemser.rest.request.login.LoginRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient extends BaseClient{
    private final String LOGIN_ENDPOINT = "/login";

    public Response login(LoginRequest loginRequest) {
        return
        given()
                .spec(super.set())
                .contentType(ContentType.JSON)
                .body(loginRequest)
        .when()
                .post(LOGIN_ENDPOINT);
    }
}

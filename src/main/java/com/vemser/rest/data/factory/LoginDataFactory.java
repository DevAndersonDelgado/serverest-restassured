package com.vemser.rest.data.factory;

import com.vemser.rest.request.login.LoginRequest;

public class LoginDataFactory extends BaseDataFactory{

    public static LoginRequest login() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(BaseDataFactory.emailProp());
        loginRequest.setPassword(BaseDataFactory.senhaProp());

        return loginRequest;
    }

    public static LoginRequest loginSenhaInvalida() {
        LoginRequest request = login();

        request.setPassword("123");

        return request;
    }

    public static LoginRequest loginEmailInvalido() {
        LoginRequest request = login();

        request.setEmail("beltrano1@qa.com.br");

        return request;
    }

    public static LoginRequest loginCamposVazios() {
        LoginRequest request = login();

        request.setEmail("");
        request.setPassword("");

        return request;
    }

}

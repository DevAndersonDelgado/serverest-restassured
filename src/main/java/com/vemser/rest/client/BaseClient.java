package com.vemser.rest.client;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseClient {
    private String baseUri = "https://serverest.dev/";

    public RequestSpecification set(){
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setConfig(RestAssured.config().logConfig(
                        LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .build()
                ;
    }

}

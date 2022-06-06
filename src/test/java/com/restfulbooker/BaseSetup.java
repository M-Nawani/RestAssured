package com.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static org.hamcrest.Matchers.lessThan;


public class BaseSetup {

    @BeforeClass
    public void setup(){
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                // Logs the request before it is passed to the HTTP BUILDER. HTTP builder and client add additional headers, this will log only what is sent in the request specification
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setBaseUri("https://restful-booker.herokuapp.com")
                .build();

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(6000L))
                .build();

        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;





    }
}

package com.herokuapp.restful_booker.api;

import com.herokuapp.restful_booker.pojo.CheckInCheckOutDatesResponse;
import com.herokuapp.restful_booker.pojo.CreateNewBookingRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiOperations {

    public static final String ACCESS_TOKEN = "YWRtaW46cGFzc3dvcmQxMjM=";

    private ApiOperations() {
    }

    public static Response performGetHealthCheckRequest(String endpoint) {

        return given()
                .then()
                .contentType(ContentType.TEXT)
                .when()
                .get(endpoint);
    }
    public static Response performPostRequest(String endpoint, Object payload) {

        return given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(endpoint);
    }

    public static Response performGetRequest(String endpoint) {

        return given()
                .then()
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint);
    }
    public static Response performGetRequestQueryParam(String endpoint, String paramKey, String paramValue){

        return given()
                .queryParam(paramKey, paramValue)
                .then()
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint);
    }
    public static Response performGetRequestPathParam(String endpoint,String paramKey, String paramValue){

        return given()
                .pathParam(paramKey, paramValue)
                .when()
                .get(endpoint);
    }
    public static Response performDeleteRequest(String endpoint, String paramKey, String paramValue){
        return given()
                .header("Authorization", "Basic "+ACCESS_TOKEN)
                .pathParams(paramKey, paramValue)
                .when()
                .delete(endpoint);
    }

    public static Response performPutRequest(String endpoint, Object payload, int id){
        RequestSpecification requestSpecification = given();

        requestSpecification = requestSpecification.header("Authorization", "Basic "+ACCESS_TOKEN);
        requestSpecification = requestSpecification.header("Cookie", "token="+ACCESS_TOKEN);

        return requestSpecification
                .pathParam("id",id)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put(endpoint);
    }
    public static Response performPatchRequest(String endpoint, Object payload, int id){
        RequestSpecification requestSpecification = given();

        requestSpecification = requestSpecification.header("Authorization", "Basic "+ACCESS_TOKEN);
        requestSpecification = requestSpecification.header("Cookie", "token="+ACCESS_TOKEN);

        return requestSpecification
                .pathParam("id",id)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch(endpoint);
    }

}


package com.herokuapp.restful_booker.tests;

import com.herokuapp.restful_booker.pojo.*;
import groovy.json.JsonToken;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static com.herokuapp.restful_booker.api.ApiOperations.*;
import static com.herokuapp.restful_booker.api_constans.ApiEndPoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static com.herokuapp.restful_booker.api_constans.ApiStatus.*;

public class BookingApiTests {
    @Test
    public void getHealthCheckTest() {
//        given()
//                .when()
//                .get(GET_HEALTH_CHECK_ENDPOINT)
//                .then()
//                .contentType(ContentType.TEXT)
//                .statusCode(201);
        Response response = performGetHealthCheckRequest(GET_HEALTH_CHECK_ENDPOINT)
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract()
                .response();
        String actualResponse = response.getBody().asString();
        Assert.assertEquals(actualResponse, "Created");
    }

    @Test
    public void createTokenTest() {

        Response response = performPostRequest(CREATE_TOKEN_ENDPOINT, new CreateTokenRequest("admin", "password123"))
                .then()
                .contentType(ContentType.JSON)
                .statusCode(OK.getStatusCode())
                .body("token", notNullValue())
                .extract()
                .response();
        CreateTokenResponse token = response.as(CreateTokenResponse.class);
        System.out.println(token);

//        Response response = given()
//                .contentType(ContentType.JSON)
//                .body(new CreateTokenRequest("admin", "password123"))
//                .post(CREATE_TOKEN_ENDPOINT)
//                .then()
//                .contentType(ContentType.JSON)
//                .statusCode(200)
//                .body("token", notNullValue())
//                .extract()
//                .response();
//        String token = response.jsonPath().getString("token");
//        System.out.println(token);
    }

    @Test
    public void getAllBookingsTest() {

        Response response = performGetRequest(GET_ALL_BOOKINGS_ENDPOINT)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(OK.getStatusCode())
                .extract()
                .response();
        AllBookingsResponse[] bookObj = response.as(AllBookingsResponse[].class);
        for (AllBookingsResponse obj : bookObj) {
            Assert.assertTrue(obj.getBookingid() != null);
        }

//        Response response = given()
//                .get(GET_ALL_BOOKINGS_ENDPOINT)
//                .then()
//                .contentType(ContentType.JSON)
//                .statusCode(200)
//                .extract()
//                .response();
//    AllBookingsResponse[] bookObj = response.as(AllBookingsResponse[].class);
//    for (AllBookingsResponse obj: bookObj) {
//        Assert.assertTrue(obj.getBookingid() != null);
//    }
    }

    public String returnParameterFromBookingById(String parameter, String id) {
        return performGetRequestPathParam(GET_SINGLE_BOOKING_ENDPOINT, "id", id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .jsonPath()
                .getString(parameter);
    }

    @Test
    public void getAllBookingsByNameTest() {
        String name = "Sally";
        String firstOrLastName = "firstname";


        Response response = performGetRequestQueryParam(GET_ALL_BOOKINGS_ENDPOINT, firstOrLastName, name)
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .response();
        AllBookingsResponse[] bookObj = response.as(AllBookingsResponse[].class);
        for (AllBookingsResponse obj : bookObj) {
            Assert.assertTrue(obj.getBookingid() != null);
            Assert.assertEquals(name, returnParameterFromBookingById(firstOrLastName, obj.getBookingid()));
        }


//        Response response = given()
//                .queryParam(firstOrLastName, name)
//                .get(GET_ALL_BOOKINGS_ENDPOINT)
//                .then()
//                .extract()
//                .response();
//    AllBookingsResponse[] bookObj = response.as(AllBookingsResponse[].class);
//    for (AllBookingsResponse obj: bookObj) {
//        Assert.assertTrue(obj.getBookingid() != null);
//        Assert.assertEquals(name,returnParameterFromBookingById(firstOrLastName, obj.getBookingid()) );
//    }

    }

    @Test
    public void getAllBookingsByCheckInTest() {
        String date = "2013-02-23";
        String checkInOrCheckOut = "checkin";

        Response response = performGetRequestQueryParam(GET_ALL_BOOKINGS_ENDPOINT, checkInOrCheckOut, date)
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .response();
        AllBookingsResponse[] bookObj = response.as(AllBookingsResponse[].class);
        for (AllBookingsResponse obj : bookObj) {
            Assert.assertTrue(obj.getBookingid() != null);
            System.out.println(obj.getBookingid());
        }

//        Response response = given()
//                .queryParam("checkin", "2013-02-23")
//                .get(GET_ALL_BOOKINGS_ENDPOINT)
//                .then()
//                .contentType(ContentType.JSON)
//                .statusCode(200)
//                .extract()
//                .response();
//    AllBookingsResponse[] bookObj = response.as(AllBookingsResponse[].class);
//    for (AllBookingsResponse obj: bookObj) {
//        Assert.assertTrue(obj.getBookingid() != null);
//        System.out.println(obj);
//    }
    }

    @Test
    public void createNewBooking() {
    CreateNewBookingRequest newBookingRequest = new CreateNewBookingRequest("John", "Smith", 690, true, new CheckInCheckOutDatesResponse("2023-12-01", "2023-12-05"), "Breakfast");

        CreateNewBookingResponse response = performPostRequest(POST_BOOKING_ENDPOINT, newBookingRequest)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(ContentType.JSON)
                .extract()
                .response()
            .as(CreateNewBookingResponse.class);
    SingleBookingByIdResponse singleBookingByIdResponse = response.getBooking();
        Assert.assertEquals(newBookingRequest.getFirstname(), singleBookingByIdResponse.getFirstname());
        Assert.assertEquals(newBookingRequest.getLastname(), singleBookingByIdResponse.getLastname());
        Assert.assertEquals(newBookingRequest.getTotalprice(), singleBookingByIdResponse.getTotalprice());
        Assert.assertEquals(newBookingRequest.getAdditionalneeds(), singleBookingByIdResponse.getAdditionalneeds());

//        CreateNewBookingResponse response = given()
//                .contentType(ContentType.JSON)
//                .body(newBookingRequest)
//                .when()
//                .post(POST_BOOKING_ENDPOINT)
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .extract()
//                .response()
//                .as(CreateNewBookingResponse.class);
//        SingleBookingByIdResponse bookingResponse = response.getBooking();
//        Assert.assertEquals(newBookingRequest.getFirstname(), bookingResponse.getFirstname());
//        Assert.assertEquals(newBookingRequest.getLastname(), bookingResponse.getLastname());
//        Assert.assertEquals(newBookingRequest.getTotalprice(), bookingResponse.getTotalprice());
//        Assert.assertEquals(newBookingRequest.getAdditionalneeds(), bookingResponse.getAdditionalneeds());

    }

    @Test
    public void updateAllRecordsBooking() {
        CreateNewBookingRequest newBookingRequest = new CreateNewBookingRequest("Rose", "Lapin", 780, true, new CheckInCheckOutDatesResponse("2023-12-01", "2023-12-05"), "Breakfast");
        int id = 190;

        SingleBookingByIdResponse response = performPutRequest(PUT_BOOKING_ENDPOINT, newBookingRequest, id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .as(SingleBookingByIdResponse.class);

        Assert.assertEquals(newBookingRequest.getFirstname(), response.getFirstname());
        Assert.assertEquals(newBookingRequest.getLastname(), response.getLastname());
        Assert.assertEquals(newBookingRequest.getTotalprice(), response.getTotalprice());
        Assert.assertEquals(newBookingRequest.getAdditionalneeds(), response.getAdditionalneeds());

//        SingleBookingByIdResponse response = given()
//                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
//                .header("Cookie", "token=YWRtaW46cGFzc3dvcmQxMjM=")
//                .pathParam("id",id)
//                .contentType(ContentType.JSON)
//                .body(newBookingRequest)
//                .when()
//                .put(PUT_BOOKING_ENDPOINT)
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .extract()
//                .response()
//                .as(SingleBookingByIdResponse.class);
//        Assert.assertEquals(newBookingRequest.getFirstname(), response.getFirstname());
//        Assert.assertEquals(newBookingRequest.getLastname(), response.getLastname());
//        Assert.assertEquals(newBookingRequest.getTotalprice(), response.getTotalprice());
//        Assert.assertEquals(newBookingRequest.getAdditionalneeds(), response.getAdditionalneeds());

    }

    @Test
    public void updateSomeRecordsBooking() {
        int id = 4;
        UpdateNameRequest nameRequest = new UpdateNameRequest("Adam", "Chome" );

        SingleBookingByIdResponse response = performPatchRequest(PATCH_BOOKING_ENDPOINT, nameRequest, id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .as(SingleBookingByIdResponse.class);
        Assert.assertEquals(nameRequest.getFirstname(), response.getFirstname());
        Assert.assertEquals(nameRequest.getLastname(), response.getLastname());

//        SingleBookingByIdResponse response = given()
//                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
//                .header("Cookie", "token=YWRtaW46cGFzc3dvcmQxMjM=")
//                .pathParam("id",id)
//                .contentType(ContentType.JSON)
//                .body(nameRequest)
//                .when()
//                .patch(PATCH_BOOKING_ENDPOINT)
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .extract()
//                .response()
//                .as(SingleBookingByIdResponse.class);
//        Assert.assertEquals(nameRequest.getFirstname(), response.getFirstname());
//        Assert.assertEquals(nameRequest.getLastname(), response.getLastname());


    }
    @Test
    public void getSingleBookingById() {
        String id = "10";

        Response response = performGetRequestPathParam(GET_SINGLE_BOOKING_ENDPOINT, "id", id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(ContentType.JSON)
                .extract()
                .response();
        SingleBookingByIdResponse singleBookingByIdResponse = response.as(SingleBookingByIdResponse.class);
        System.out.println(singleBookingByIdResponse.getLastname());


//            Response response = given()
//                 .pathParam("id", id)
//                 .when()
//                .get(GET_SINGLE_BOOKING_ENDPOINT)
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .extract()
//                .response();
//        String name = response.jsonPath().getString("firstname");
//        System.out.println(name);


    }

    @Test
    public void deleteBooking() {
        String id = "3";

    Response response = performDeleteRequest(DELETE_BOOKING_ENDPOINT, "id", id)
             .then()
             .statusCode(CREATED.getStatusCode())
            .contentType(ContentType.TEXT)
            .extract()
            .response();
        String actualResponse = response.getBody().asString();
        Assert.assertEquals(actualResponse, "Created");
        confirmBookingDeleted(id);


//        Response response = given()
//                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
//                .queryParam("id", id)
//                .when()
//                .delete("https://restful-booker.herokuapp.com/booking/" + id)
//                .then()
//                .statusCode(201)
//                .contentType(ContentType.TEXT)
//                .extract()
//                .response();
//        String actualResponse = response.getBody().asString();
//        Assert.assertEquals(actualResponse, "Created");
//        confirmBookingDeleted(id);

    }

    public boolean confirmBookingDeleted(String id) {

        Response response = performGetRequestPathParam(GET_SINGLE_BOOKING_ENDPOINT, "id", id)
                .then()
                .statusCode(NOT_FOUND.getStatusCode())
                .contentType(ContentType.TEXT)
                .extract()
                .response();
        String actualResponse = response.getBody().asString();

        return (actualResponse.equalsIgnoreCase( "Not Found")) ;


    }

}

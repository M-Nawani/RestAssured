package com.restfulbooker;

import data.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RestfulBookerE2ETests extends BaseSetup {
    private BookingData newBooking;
    private int bookingId;
    private TokenCreds newToken;
    private BookingData updateBooking;
    private PartialBookingData partialUpdate;

    @BeforeTest
    public void testSetup(){
        BookingDataBuilder builder = new BookingDataBuilder();
        newBooking = builder.bookindatabuilder();
        TokenBuilder tokenid = new TokenBuilder();
        newToken = tokenid.tokenbuilder();
        updateBooking = builder.bookindatabuilder();
        partialUpdate = builder.partialdatabuilder();
    }

    @Test
    public void createBooking(){
        bookingId = given()
                .body(newBooking)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .assertThat()
                .body("booking.firstname", equalTo(newBooking.getFirstname()))
                .body("booking.lastname",equalTo(newBooking.getLastname()))
                .body("bookingid", notNullValue())
                .body("booking.totalprice", equalTo(newBooking.getTotalprice()))
                .body("booking.depositpaid", equalTo(newBooking.isDepositpaid()))
                .body("booking.additionalneeds", equalTo(newBooking.getAdditionalneeds()))
                .body("booking.bookingdates.checkin",equalTo(newBooking.getBookingdates().getCheckin()))
                .body("booking.bookingdates.checkout",equalTo(newBooking.getBookingdates().getCheckout()))
                .extract()
                .path("bookingid");
    }

    @Test(priority = 1)
    public void getBooking(){
        given()
                .get("/booking/" +bookingId)
                .then()
                .statusCode(200)
                .assertThat()
                .body("firstname", equalTo(newBooking.getFirstname()))
                .body("lastname",equalTo(newBooking.getLastname()))
                .body("totalprice", equalTo(newBooking.getTotalprice()))
                .body("depositpaid", equalTo(newBooking.isDepositpaid()))
                .body("additionalneeds", equalTo(newBooking.getAdditionalneeds()))
                .body("bookingdates.checkin",equalTo(newBooking.getBookingdates().getCheckin()))
                .body("bookingdates.checkout",equalTo(newBooking.getBookingdates().getCheckout()));
    }

    @Test(priority = 2)
    public void updateBooking(){
        given()
                .body(updateBooking)
                .header("Cookie","token="+ requestToken())
                .when()
                .put("booking/"+ bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo(updateBooking.getFirstname()))
                .body("lastname",equalTo(updateBooking.getLastname()))
                .body("totalprice", equalTo(updateBooking.getTotalprice()))
                .body("depositpaid", equalTo(updateBooking.isDepositpaid()))
                .body("additionalneeds", equalTo(updateBooking.getAdditionalneeds()))
                .body("bookingdates.checkin",equalTo(updateBooking.getBookingdates().getCheckin()))
                .body("bookingdates.checkout",equalTo(updateBooking.getBookingdates().getCheckout()));
    }

    @Test(priority = 3)
    public void updatePartialBooking(){
        given()
                .body(partialUpdate)
                .header("Cookie", "token=" +requestToken())
                .when()
                .patch("/booking/"+bookingId)
                .then()
                .statusCode(200)
                .assertThat()
                .body("firstname", equalTo(partialUpdate.getFirstname()))
                .body("lastname",equalTo(updateBooking.getLastname()))
                .body("totalprice", equalTo(partialUpdate.getTotalprice()))
                .body("depositpaid", equalTo(updateBooking.isDepositpaid()))
                .body("additionalneeds", equalTo(updateBooking.getAdditionalneeds()))
                .body("bookingdates.checkin",equalTo(updateBooking.getBookingdates().getCheckin()))
                .body("bookingdates.checkout",equalTo(updateBooking.getBookingdates().getCheckout()));
    }

    @Test(priority = 4)
    public void deleteBooking(){
        given()
                .header("Cookie", "token="+requestToken())
                .when()
                .delete("/booking/"+bookingId)
                .then()
                .statusCode(201);
    }

    @Test(priority = 5)
    public void verifyBookingDeleted(){
        given()
                .get("/booking/" +bookingId)
                .then()
                .statusCode(404);
    }

    public String requestToken(){
         return given()
                .body(newToken)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

    }
}

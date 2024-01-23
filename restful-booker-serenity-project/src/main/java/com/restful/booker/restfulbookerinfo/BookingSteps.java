package com.restful.booker.restfulbookerinfo;

import com.restful.booker.constonts.EndPoints;
import com.restful.booker.model.AuthorizationPojo;
import com.restful.booker.model.BookingDates;
import com.restful.booker.model.BookingPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

public class BookingSteps {
    @Step("Generating authorisation token with Username : admin and Password : password123")
    public String test001createAuthorisationToken() {

        AuthorizationPojo authorizationPojo = new AuthorizationPojo();
        authorizationPojo.setUserName("admin");
        authorizationPojo.setPassword("password123");

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(authorizationPojo)
                .post()
                .then().log().all().statusCode(200)
                .extract().path("token");
    }

    @Step("Creating student with firstName : {0}, lastName : {1}, totalPrice : {2}, depositPaid : {3}, additionalNeeds : {4} and bookingDates : {5}")
    public ValidatableResponse test002createBooking(String firstname, String lastname, int totalprice,
                                                    boolean depositpaid, String additionalneeds, BookingDates bookingDates) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setAdditionalneeds(additionalneeds);
        bookingPojo.setBookingdates(bookingDates);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer c3cf9df32fa20c9")
                .body(bookingPojo)
                .when()
                .post()
                .then().log().all();
    }

//    @Step("Getting the booking information by bookingID : {0}")
//    public HashMap<String, Object> getStudentInfoByFirstName(String firstName) {
//
//        String s1 = "findAll{it.firstName == '";
//        String s2 = "'}.get(0)";
//
//        HashMap<String, Object> studentMap = SerenityRest.given()
//                .when()
//                .get(EndPoints.GET_ALL_STUDENT)
//                .then()
//                .log().all()
//                .statusCode(200)
//                .extract()
//                .path(s1 + firstName + s2);
//
//        return studentMap;
//    }

    @Step("Updating booking with firstName : {0}, lastName : {1}, totalPrice : {2}, depositPaid : {3}, additionalNeeds : {4}, bookingDates : {5} and token : {6}")
    public ValidatableResponse test003updateBookingDetails(String firstname, String lastname, int totalprice, boolean depositpaid,
                                                           String additionalneeds, BookingDates dates, String token, int bookingID) {

        BookingPojo bookingPojo = new BookingPojo();

        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setAdditionalneeds(additionalneeds);
        bookingPojo.setBookingdates(dates);

        System.out.println("Token: " + token);


        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .pathParam("bookingID", bookingID)
                .body(bookingPojo)
                .when()
                .put(EndPoints.UPDATE_BOOKING_BY_ID)
                .then().log().all();
    }

    @Step("Partially Update booking with firstName : {0}, lastName : {1},  bookingDates : {2} and token : {3}")
    public ValidatableResponse test04partialUpdateBookingDetails(String firstname, String lastname, BookingDates bookingdates, String token, int bookingID) {

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setBookingdates(bookingdates);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .pathParam("bookingID", bookingID)
                .body(bookingPojo)
                .when()
                .patch(EndPoints.UPDATE_BOOKING_BY_ID)
                .then().log().all();
    }

    @Step("Deleting the booking information with token : {0}")
    public ValidatableResponse test004deteleBooking(String token, int bookingID) {

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .pathParam("bookingID", bookingID)
                .when()
                .delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then().log().all();
    }

}

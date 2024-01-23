package com.restful.booker.crudtest;
import com.restful.booker.constonts.Path;
import com.restful.booker.model.BookingDates;
import com.restful.booker.restfulbookerinfo.BookingSteps;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookingCRUDTest extends TestBase {
    static int bookingID;
    static String token;

    @Steps
    BookingSteps steps;

    @Title("This will create an authorisation token")
    @Test
    public void test000createToken()
    {
        RestAssured.basePath = Path.AUTHENTICATION;
        token = steps.test001createAuthorisationToken();
        System.out.println("Token = " + token);
    }

    @Title("This will create a new booking")
    @Test
    public void test001CreateBooking() {
        System.out.println("=========== CREATE BOOKING ===============");

        RestAssured.basePath = Path.BOOKING;

        BookingDates dates = new BookingDates();
        dates.setCheckin("2024-03-04");
        dates.setCheckout("2024-04-04");

        String firstname = TestUtils.getRandomValue() + "Prime";
        String lastname = TestUtils.getRandomValue() + "Testing";
        int totalprice = 123;
        boolean depositpaid = true;
        String additionalneeds = "Dinner";

        ValidatableResponse response = steps.test002createBooking(firstname, lastname, totalprice, depositpaid, additionalneeds, dates);
        response.statusCode(200);

        bookingID = response.extract().path("bookingid");
        //System.out.println("ID = " + bookingID);
    }

    @Title("Update the booking information and verify the updated information")
    @Test
    public void test002UpdateBooking() {
        System.out.println("=========== UPDATE BOOKING USING PUT ===============");

        RestAssured.basePath = Path.BOOKING;

        BookingDates dates = new BookingDates();
        dates.setCheckin("2024-03-04");
        dates.setCheckout("2024-04-04");

        String firstname = TestUtils.getRandomValue() + "JAVA";
        String lastname = TestUtils.getRandomValue() + "Testing";
        int totalprice = 123;
        boolean depositpaid = true;
        String additionalneeds = "Dinner & bed";

        ValidatableResponse response = steps.test003updateBookingDetails(firstname, lastname, totalprice, depositpaid, additionalneeds, dates, token, bookingID);
        response.statusCode(200);
    }

    @Title("Partially Update the booking information and verify the updated information")
    @Test
    public void test003PartialUpdateBooking() {

        System.out.println("=========== PARTIAL UPDATE BOOKING ===============");

        String firstname = TestUtils.getRandomValue() + "API";
        String lastname = TestUtils.getRandomValue() + "Testing";

        BookingDates dates = new BookingDates();
        dates.setCheckin("2024-03-04");
        dates.setCheckout("2024-04-04");

        ValidatableResponse response = steps.test04partialUpdateBookingDetails(firstname, lastname, dates, token, bookingID);
        response.statusCode(200);
    }

    @Title("This will delete the booking")
    @Test
    public void test004DeleteBooking() {

        System.out.println("=========== DELETE BOOKING ===============");

        ValidatableResponse response = steps.test004deteleBooking(token, bookingID);
        response.statusCode(201);
    }

}
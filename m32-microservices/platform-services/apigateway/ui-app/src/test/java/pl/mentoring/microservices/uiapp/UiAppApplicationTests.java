package pl.mentoring.microservices.uiapp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UiAppApplicationTests {

    @Test
    void whenSendRequestToFooResource_thenOK() {
        Response response = RestAssured.get("http://localhost:8082/foos/1");

        assertEquals(200, response.getStatusCode());
    }

}

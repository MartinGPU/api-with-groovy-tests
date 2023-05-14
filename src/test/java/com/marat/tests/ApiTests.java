package com.marat.tests;

import com.marat.model.LombokUserData;
import org.junit.jupiter.api.Test;

import static com.marat.specs.Specs.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTests {

    @Test
    public void getSingleUser() {
        LombokUserData data = given()
                        .spec(request)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(response200)
                        .log().body()
                        .extract().as(LombokUserData.class);
        assertEquals(2, data.getUser().getId());
        assertEquals("janet.weaver@reqres.in", data.getUser().getEmail());
        assertEquals("Janet", data.getUser().getFirstName());
        assertEquals("Weaver", data.getUser().getLastName());
    }

    @Test
    public void createUser() {
        given()
                .spec(request)
                .body("{\"name\": \"Donatello\", \"job\": \"peace maker\"}")
                .when()
                .post("/users")
                .then()
                .spec(response201)
                .body("name", is("Donatello"))
                .body("job", is("peace maker"));
 }

    @Test
    public void updateUserData() {
        given()
                .spec(request)
                .body("{\"name\": \"Donald\", \"job\": \"teacher\"}")
                .when()
                .put("/users/2")
                .then()
                .spec(response200)
                .body("name",  is("Donald"))
                .body("job",  is("teacher"));
    }

    @Test
    public void dropUserData() {
        given()
                .spec(request)
                .body("{\"name\": \"Donald\", \"job\": \"teacher\"}")
                .when()
                .delete("/users/2")
                .then()
                .spec(response204);
    }

    @Test
    public void userRegistration() {
        given()
                .spec(request)
                .body("{\"email\": \"rachel.howell@reqres.in\", \"password\": \"lineage\"}")
                .when()
                .post("/register")
                .then()
                .spec(response200)
                .body("id",  is(12))
                .body("token",  is("QpwL5tke4Pnpja7X12"));
    }

    @Test
    public void userAuth() {
        given()
                .spec(request)
                .body("{\"email\": \"rachel.howell@reqres.in\", \"password\": \"lineage\"}")
                .when()
                .post("/login")
                .then()
                .spec(response200)
                .body("token",  is("QpwL5tke4Pnpja7X12"));
    }
}

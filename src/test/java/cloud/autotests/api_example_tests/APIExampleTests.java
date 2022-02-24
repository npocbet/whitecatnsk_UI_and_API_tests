package cloud.autotests.api_example_tests;

import cloud.autotests.api_example_tests.lombok.*;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cloud.autotests.api_example_tests.filters.CustomLogFilter.customLogFilter;
import static cloud.autotests.api_example_tests.specs.WhiteCatNskSpec.reqresRequestSpec;
import static cloud.autotests.api_example_tests.specs.WhiteCatNskSpec.responseSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;

public class APIExampleTests {

    @Test
    @DisplayName("Check wikipedia response after editing some unprotected page")
    void wikipediaCheckEditSomeUnprotectedPageTest() throws IOException {
        File exampleFile = new File("src/test/resources/stringDataForAPIExampleTests.txt");
        String data = IOUtils.toString(new FileReader(exampleFile));

        given()
                .filter(customLogFilter().withCustomTemplates())
                .log().all()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("ruwikimwuser-sessionId", "1240b49167c216743a2f")
                .cookie("ruwikiel-sessionId", "486a796344f350e73f7a")
                .body(data)
                .when()
                .post("https://ru.wikipedia.org/w/api.php")
                .then()
                .spec(responseSpec)
                .body(matchesJsonSchemaInClasspath("schemas/getWikipediaStashedTestSchema.json"));
    }

    @Description("Date of create user should be current")
    @Test
    void createAUser(){
        String newUserData = "{\"name\": \"morpheus\", \"job\": \"leader\"}";
        Date date = new Date();
        String response = given()
                .contentType(ContentType.JSON)
                .body(newUserData)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract()
                .response()
                .path("createdAt");
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        assertThat(response).contains(formatForDateNow.format(date));
    }

    @Test
    @DisplayName("Create user via lombok")
    void createUserLombok() {
        NewUser newCreateuser = new NewUser("Svjato", "QA");

        NewUserResponse response = given()
                .spec(reqresRequestSpec)
                .body(newCreateuser)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().as(NewUserResponse.class);

        assertThat(response.getId()).isNotBlank();
        assertThat(response.getCreatedAt()).isNotBlank();
    }

    @Test
    @DisplayName("Check size of userlist")
    void getUserListwithLombokAndCheckSizeTest() {
        Users data =
                given()
                        .spec(reqresRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .log().body()
                        .extract().as(Users.class);
        assertThat(data.getData().size()).isEqualTo(6);
    }

    @Test
    @DisplayName("Get single user not found")
    void getSingleUserNotFound() {
        given()
                .spec(reqresRequestSpec)
                .when()
                .get("/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Update user lombok")
    void updateUserLombok() {
        NewUser newCreateuser = new NewUser("morpheus", "zion resident");

        NewUserResponse response = given()
                .spec(reqresRequestSpec)
                .body(newCreateuser)
                .when()
                .put("/users/2")
                .then()
                .spec(responseSpec)
                .extract().as(NewUserResponse.class);

        assertThat(response.getUpdatedAt()).isNotBlank();
    }

    @Test
    public void checkEmailUsingGroovy() {
        given()
                .spec(reqresRequestSpec)
                .when()
                .get("/users")
                .then()
                .log().body()
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("tracey.ramos@reqres.in"));
    }

    @Test
    void checkSometh() {
                given()
                        .spec(reqresRequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .log().body()
                        .body("data", hasEntry("last_name", "Weaver"));
    }
}

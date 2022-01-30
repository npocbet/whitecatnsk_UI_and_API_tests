package cloud.autotests.api_tests;
import com.codeborne.selenide.Condition;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static cloud.autotests.api_tests.filters.CustomLogFilter.customLogFilter;
import static cloud.autotests.api_tests.specs.WhiteCatNskSpec.requestSpec;
import static cloud.autotests.api_tests.specs.WhiteCatNskSpec.responseSpec;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SomeAPITests extends TestBase {

    @Test
    void addProductToBasketViaAPITest() {

        step("Get product ID", () -> {
            productId = $(".product-item").getAttribute("id").split("_")[2];
        });

        step("add product to the buscet via API", () -> {

            given()
                    .spec(requestSpec)
                    .contentType("text/html; charset=utf-8")
                    .cookie("PHPSESSID", cookie)
                    .when()
                    .get("/local/include/ajax.php?target=add2basket&elementID=" + productId + "&quantity=1")
                    .then()
                    .spec(responseSpec);

        });

        step("Open cart", () -> {
            open("/cart/");
        });

        step("Check product in the cart", () -> {
            int size = $$(".cart-table__row").filter(Condition.attribute("data-pid", productId)).size();
            Assertions.assertThat(size).isEqualTo(1);
        });

    }

    @Test
    void increaseTheNumberOfItemsInTheCartViaAPITest() {

        step("Get product ID", () -> {
            productId = $(".product-item").getAttribute("id").split("_")[2];
        });

        step("add product to the buscet via API", () -> {
            // TODO Дописать LOmBOK
            given()
                    .spec(requestSpec)
                    .contentType("text/html; charset=utf-8")
                    .cookie("PHPSESSID", cookie)
                    .when()
                    .get("/local/include/ajax.php?target=add2basket&elementID=" + productId + "&quantity=1")
                    .then()
                    .spec(responseSpec);
        });

        step("Get row ID", () -> {
            open("/cart/");
            rowID = $$(".cart-table__row").filter(Condition.attribute("data-pid", productId)).first().getAttribute("data-id");
        });

        step("add product to the buscet via API", () -> {
            String data = "id=" + rowID + "&product=" + productId + "&quantity=2";

            // TODO Дописать LOmBOK
            Response response =
                    given()
                            .spec(requestSpec)
                            .cookie("PHPSESSID", cookie)
                            .body(data)
                            .contentType("text/html; charset=utf-8")
                            .when()
                            .post("/api/basket/update_item.php")
                            .then()
                            .spec(responseSpec)
                            .extract()
                            .response();

            System.out.println(response);
        });

        step("Open cart", () -> {
            open("/cart/");
        });

        step("Check product in the cart", () -> {
            int size = $$(".cart-table__row").filter(Condition.attribute("data-pid", productId)).size();
            System.out.println(size);
        });

    }

    @Test
    void someWithJSONResponseTest() {
               given()
                        .spec(requestSpec)
                        .contentType(ContentType.JSON)
                        .when()
                        .get("https://node-eu1-c-3.jivosite.com/widget/status/327165/KTtJRT19eK?rnd=0.07261789037131228")
                        .then()
                        .spec(responseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/getSomeAPITestsSchema.json"));

    }

    @Test
    void wikipediaEditSomeUnprotectedPage(){
        String data = "action=stashedit&format=json&formatversion=2&title=%D0%A6%D0%B8%D0%BC%D0%B1%D0%B0%D0%BB%D1%8E%D0%BA" +
                "&section=&sectiontitle=&summary=&contentmodel=wikitext&contentformat=text%2Fx-wiki&baserevid=61150812" +
                "&text=%23REDIRECT+%5B%5B%D0%A6%D1%8B%D0%BC%D0%B1%D0%B0%D0%BB%D1%8E%D0%BA%5D%5D%0A%D1%84%D1%8B%D0%B2%D" +
                "0%BB%D1%8B%D0%B4%D0%B2%D0%BB%D0%B4%D0%BB%D0%B4%D0%BB%D1%8B%D0%B2%D1%84%D1%8B%D0%B2&token=%2B%5C";


        Response response =
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
                        //.body(matchesJsonSchemaInClasspath("schemas/getSomeAPITestsSchema.json"))
                        .extract()
                        .response();

        System.out.println(response);
    }
}

package cloud.autotests.ui_api_tests;
import com.codeborne.selenide.Condition;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cloud.autotests.api_example_tests.specs.WhiteCatNskSpec.whiteCatNskRequestSpec;
import static cloud.autotests.api_example_tests.specs.WhiteCatNskSpec.responseSpec;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class WhiteCatNSKAPITests extends TestBase {

    @Test
    @DisplayName("add product to the cart via API")
    void addProductToBasketViaAPITest() {
        step("Open main page", () -> open("/"));
        step("Get session cookie", () ->
            cookie = String.valueOf(webdriver().driver().getWebDriver().manage().getCookieNamed("PHPSESSID").getValue()));
        step("Get product ID", () ->
            productId = $(".product-item").getAttribute("id").split("_")[2]);
        step("add product to the buscet via API", () -> {
            given()
                    .spec(whiteCatNskRequestSpec)
                    .contentType("text/html; charset=utf-8")
                    .cookie("PHPSESSID", cookie)
                    .when()
                    .get("/local/include/ajax.php?target=add2basket&elementID=" + productId + "&quantity=1")
                    .then()
                    .spec(responseSpec);
        });
        step("Open cart", () -> open("/cart/"));
        step("Check product in the cart", () -> {
            int size = $$(".cart-table__row").filter(Condition.attribute("data-pid", productId)).size();
            Assertions.assertThat(size).isEqualTo(1);
        });
    }

    @Test
    @DisplayName("some test with json response")
    void someWithJSONResponseTest() {
               given()
                        .spec(whiteCatNskRequestSpec)
                        .contentType(ContentType.JSON)
                        .when()
                        .get("https://node-eu1-c-3.jivosite.com/widget/status/327165/KTtJRT19eK?rnd=0.07261789037131228")
                        .then()
                        .spec(responseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/getWhiteCatNSKAPITestsSchema.json"));
    }
}

package cloud.autotests.ui_api_tests;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cloud.autotests.utils.RandomUtils.getRandomInt;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class BasketTests extends TestBase {
    @Test
    @DisplayName("Products quantity after adding to the buscet and increment")
    @Description("Quantity of the product after adding to the buscet and increment should be equal 2")
    void ProductQuantityTest() {
        step("Open main page", () -> open("/"));
        step("Open the first product", () ->
                $(".product-item__image").click());
        step("Add product to the buscet", () -> {
            productName = $(".product-head__title").text();
            $(".product-price-btn_green").click();
        });
        step("Tap + to add one more item to the buscet", () -> $(".pluP").click());
        step("Open the buscet", () -> $(".product-price-btn_green").click());
        step("Find product on the buscet. Its quantity should be equal 2", () -> {
            //.cart-table__body .cart-table__row .counter .value
            int quantityOfProduct = Integer.parseInt($$(".column__name").filter(Condition.text(productName))
                    .first().parent().$(".column__quantity .value").text());
            assertThat(quantityOfProduct).isEqualTo(2);
        });
    }

    @Test
    @DisplayName("Products quantity after double adding to the buscet")
    @Description("Quantity of the product after multiple adding to the buscet should be equal number of multiplies")
    void ProductQuantityTest2() {
        //1st time
        step("Open main page", () -> open("/"));
        step("Open the first product", () -> $(".product-item__image").click());
        step("Add product to the buscet", () -> {
            productName = $(".product-head__title").text();
            $(".product-price-btn_green").click();
        });
        //2nd time
        step("Open main page", () -> open("/"));
        step("Open the first product", () -> $(".product-item__image").click());
        step("Add product to the buscet", () -> {
            productName = $(".product-head__title").text();
            $(".product-price-btn_green").click();
        });

        step("Find count label on the buscet. Its quantity should be equal 1", () ->
            $(".header-item__count").shouldBe(Condition.text("2")));
    }
}

package cloud.autotests.tests;

import cloud.autotests.helpers.DriverUtils;
import com.codeborne.selenide.*;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cloud.autotests.utils.RandomUtils.getRandomInt;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;


public class CatalogTests extends TestBase {

    @Test
    @DisplayName("Products on a random slider page")
    @Description("slider element is available and there are some products on a random slider page")
    void sliderTest(){
        step("Open main page", () -> {
            open("/");
        });

        step("Count number of slides", () -> {
            numberOfSlides = $$(".swiper-pagination span").size();
        });

        // TODO .swiper-pagination .swiper-pagination-bullet tabindex??
        step("click random slide", () -> {
            currentSlide = getRandomInt(1, numberOfSlides);
            $(".swiper-pagination").scrollTo();
            $(".swiper-pagination").$("[aria-label=\"Go to slide " + currentSlide + "\"]").click();
            $(".swiper-container").click();
        });

        step("number of elements should be more than 0", () -> {
            int numberOfElements = $$(".js-more-container .product-item").size();
            System.out.println("Should be numberOfElements more than 0, and numberOfElements is " + numberOfElements +
                    " on the slide " + currentSlide);
            assertThat(numberOfElements).isGreaterThan(0);
        });
    }

    @Test
    @DisplayName("Products on category page")
    @Description("Catalog is available and there are some products on a random category page")
    void CategoryTest(){
        step("Open main page", () -> {
            open("/");
        });

        step("Open the catalog", () -> {
            $(".bam-ip-confirm-button-yes").click();
            $("#catalog-button").click();
        });

        step("Count number of categories", () -> {
            numberOfCategories = $$(".header-submenu .header-submenu__title").size();
            System.out.println(numberOfCategories);
        });

        step("click random category", () -> {
            currentCategory = getRandomInt(0, numberOfCategories - 1);
            // TODO $(".header-submenu .header-submenu__title").sibling(currentCategory - 1).scrollTo().click();
            $(".header-submenu .header-submenu__title").click();
        });

        step("number of elements should be more than 0", () -> {
            int numberOfElements = $$(".js-more-container .product-item").size();
            System.out.println("Should be numberOfElements more than 0, and numberOfElements is " + numberOfElements +
                    " on the slide " + currentSlide);
            assertThat(numberOfElements).isGreaterThan(0);
        });
    }

    // TODO Написать тест, чтобы у всех товаров в разделе акции была надпись акция и снижена цена

}
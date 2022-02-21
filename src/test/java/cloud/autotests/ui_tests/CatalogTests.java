package cloud.autotests.ui_tests;

import cloud.autotests.pages.CatalogPage;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Description;
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
    void sliderTest() {
        catalogPP.openPage();
        numberOfSlides = catalogPP.countNumberOfSlides();
        catalogPP.clickRandomSlide(numberOfSlides).numberOfElementsShouldBeMoreThanZero();
    }

    @Test
    @DisplayName("Products on category page")
    @Description("Catalog is available and there are some products on a random category page")
    void CategoryTest() {
        catalogPP.openPage();
        catalogPP.openTheCatalog();
        numberOfCategories = catalogPP.categories.size();
        catalogPP.clickRandomCategory(numberOfCategories)
                .numberOfElementsShouldBeMoreThanZero();
    }

    @Test
    @DisplayName("All promo products is realy promo")
    @Description("All promo products should have a label акция")
    void PromotionTest() {

        catalogPP.openPromoPage()
                .openAllThePromoProducts()
                .numberOfPromosShouldBeTheSameAsNumberOfElements();

    }

    @Test
    @DisplayName("Random product has all UI-elements")
    @Description("All of products should have all standart UI-elements")
    void TestProductUIElements() {

        catalogPP.openPage()
                .clickProduct()
                .checkUIElementTitle()
                .checkUIElementPrice()
                .checkUIElementBuscetButton()
                .checkUIElementCreditButton()
                .checkUIElementImage();
    }

}
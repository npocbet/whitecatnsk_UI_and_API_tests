package cloud.autotests.ui_api_tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        naboryPP.openPromoPage()
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
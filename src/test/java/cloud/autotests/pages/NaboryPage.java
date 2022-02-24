package cloud.autotests.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static cloud.autotests.utils.RandomUtils.getRandomInt;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NaboryPage {
    // locators & elements
    private final SelenideElement promoProductsButton = $$("[rel=nofollow]").last();
    private final ElementsCollection products = $$(".product-item");
    private final ElementsCollection promoLabels = $$(".product-item__label");

    // actions
    public NaboryPage openPromoPage() {
        open("/catalog/nabory/");
        return this;
    }

    public NaboryPage openAllThePromoProducts() {
        promoProductsButton.click();
        return this;
    }

    public void numberOfPromosShouldBeTheSameAsNumberOfElements() {
        assertThat(products.size()).isEqualTo(promoLabels.size());
    }

}

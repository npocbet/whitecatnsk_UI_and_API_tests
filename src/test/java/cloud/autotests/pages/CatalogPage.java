package cloud.autotests.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static cloud.autotests.utils.RandomUtils.getRandomInt;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CatalogPage {
    // locators & elements
    private final SelenideElement sliredScroll = $(".swiper-pagination");
    private final SelenideElement slider = $(".swiper-container");
    private final SelenideElement applyLocationButton = $$(".bam-ip-confirm-button-yes").last();
    private final SelenideElement catalogButton = $("#catalog-button");
    private final SelenideElement category = $(".header-submenu .header-submenu__title");
    private final SelenideElement product = $(".product-item__image");
    private final SelenideElement productTitle = $(".product-head__title");
    private final SelenideElement productPrice = $(".product-num__price");
    private final SelenideElement productBuscetButton = $(".product-price-btn_green");
    private final SelenideElement productCreditButton = $("a.line-elem_list");
    private final SelenideElement productImage = $("div.big_pic a");

    private final ElementsCollection slides = $$(".swiper-pagination span");
    private final ElementsCollection elements = $$(".js-more-container .product-item");
    public ElementsCollection categories = $$(".header-submenu .header-submenu__title");

    // actions
    public CatalogPage openPage() {
        open("/");
        return this;
    }

    public void openTheCatalog() {
        applyLocationButton.click();
        catalogButton.click();
    }

    public int countNumberOfSlides() {
        return slides.size();
    }

    public CatalogPage clickRandomSlide(int numberOfSlides) {
        int currentSlide = getRandomInt(1, numberOfSlides);
        sliredScroll.scrollTo();
        sliredScroll.$("[aria-label=\"Go to slide " + currentSlide + "\"]").click();
        slider.click();
        return this;
    }

    public CatalogPage clickRandomCategory(int numberOfCategories) {
        int currentCategory = getRandomInt(0, numberOfCategories - 1);
        // TODO $(".header-submenu .header-submenu__title").sibling(currentCategory - 1).scrollTo().click();
        category.click();
        return this;
    }

    public CatalogPage clickProduct(){
        product.click();
        return this;
    }

    public void numberOfElementsShouldBeMoreThanZero() {
        assertThat(elements.size()).isGreaterThan(0);
    }

    public CatalogPage checkUIElementTitle(){
        productTitle.shouldBe(Condition.enabled);
        return this;
    }

    public CatalogPage checkUIElementPrice(){
        productPrice.shouldBe(Condition.enabled);
        return this;
    }

    public CatalogPage checkUIElementBuscetButton(){
        productBuscetButton.shouldBe(Condition.enabled);
        return this;
    }

    public CatalogPage checkUIElementCreditButton(){
        productCreditButton.shouldBe(Condition.enabled);
        return this;
    }

    public void checkUIElementImage(){
        productImage.shouldBe(Condition.enabled);
    }
}

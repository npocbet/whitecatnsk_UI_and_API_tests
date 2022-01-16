package cloud.autotests.tests;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

import static cloud.autotests.utils.RandomUtils.getRandomString;

public class RegistrationTests extends TestBase{

    private Faker faker = new Faker();

    private String email = faker.internet().emailAddress();


    @Test
    @DisplayName("Title of the email field")
    @Description("Title of the email field should have text почта")
    void EmailRegistrationTest1() {
        step("Open main page", () -> {
            open("/");
        });

        step("Open sing in form", () -> {
            $("#prof-auth").click();
        });

        step("Chose sign in by emal", () -> {
            $(".basic-popup__link").click();
        });

        step("Chose sign in by emal", () -> {
            $$(".basic-popup__label").first().shouldHave(text("почта"));
        });

    }

    @Test
    @Disabled("email registration doesn't work")
    @DisplayName("Products on category page")
    @Description("Catalog is available and there are some products on a random category page")
    void EmailRegistrationTest2() {
        step("Open main page", () -> {
            open("/");
        });

        step("Open sing in form", () -> {
            $("#prof-auth").click();
        });

        step("Chose sign in by emal", () -> {
            $(".basic-popup__link").click();
        });

        step("Enter an email", () -> {
            $(".basic-input_phone").sendKeys(email);
        });

        step("Enter password", () -> {
            $(".basic-input_password").sendKeys(getRandomString(8));
        });

        step("Submit emal and password", () -> {
            $(".action__login_password").click();
            sleep(20000);
        });

    }

}

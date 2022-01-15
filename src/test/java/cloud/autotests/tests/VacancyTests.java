package cloud.autotests.tests;

import cloud.autotests.helpers.DriverUtils;
import com.codeborne.selenide.*;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.link;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class VacancyTests extends TestBase {

    @Test
    @Description("Test tries to find some QA vacancies on Ozon")
    @DisplayName("Ozon has some QA vacancies")
    void qaVacancyAvailableTest() {

        step("Open /vacancy/?department=Ozon%20Fintech&experience=%D0%9E%D1%82%201%20%D0%B3%D0%BE%D0%B4%D0%B0%20%D0%B4%D0%BE%203%20%D0%BB%D0%B5%D1%82&query=%D0%B0%D0%B2%D1%82%D0%BE%D0%BC%D0%B0", () -> {
            open("/vacancy/?department=Ozon%20Fintech&experience=%D0%9E%D1%82%201%20%D0%B3%D0%BE%D0%B4%D0%B0%20%D0%B4%D0%BE%203%20%D0%BB%D0%B5%D1%82&query=%D0%B0%D0%B2%D1%82%D0%BE%D0%BC%D0%B0");
        });

        step("Select city St-Petersburg", () -> {
            $("[placeholder=\"Город\"]").click();
            $$(".select__list__item").filterBy(text("Санкт-Петербург")).first().click();
            $(byText("Сохранить")).click();
        });

        step("results__items wr", () -> {
            step("Number of vacancies should be more then 1", () -> {
                int number_of_vacancies = $$(".vacancy .desktop .wr").size();

                assertThat(number_of_vacancies).isGreaterThan(1);
            });
        });
    }

    @Test
    @Description("checking internship route response")
    @DisplayName("Internship scenario response")
    void internshipRouteResponseTest(){

        step("Open https://job.ozon.ru/", () -> {
            open("/");
        });

        step("Select city St-Petersburg", () -> {
            $("[placeholder=\"Город\"]").click();
            $$(".select__list__item").filterBy(text("Санкт-Петербург")).first().click();
            $(byText("Сохранить")).click();
        });

        step("going to internships page", () -> {
            $("[href=\"/internships/\"]").click();
        });

        step("click wanna be with you", () -> {
            $(".about__top__text .button.button.button-regular.button-normal").click();
        });

        step("pick the firstt vacancy", () -> {
            $(".results__items .wr").click();
        });

        step("respond on vacancy", () -> {
            Selenide.switchTo().window(1);
            $(".vacancy .vacancy__actions__apply").click();
        });

        step("enter some test values", () -> {
            $("input[name=\"lname\"]").setValue("Кравц");
            $("input[name=\"fname\"]").setValue("Свято");
            $("input[name=\"email\"]").setValue("poco@mail.ru");
            $("input[type=\"tel\"]").setValue("+79207007070");
            $("input[type=\"file\"]").uploadFromClasspath("doc/test.pdf");
            $(".agree .checkbox").click();
            try {
                $(".button.confirm").click();
                sleep(2000);
                while ($$(".modal button").size() != 0){
                    $(".modal button").click();

                    $(".button.confirm").click();
                }
            }
            finally {
                $("#__layout .sent h4").shouldHave(text("Спасибо!"));
            }

        });
    }

    @Test
    @Description("checking internship route sharelink")
    @DisplayName("Internship scenario sharelink")
    void internshipRouteShareLinkTest(){

        step("Open https://job.ozon.ru/", () -> {
            open("/");
        });

        step("Select city St-Petersburg", () -> {
            $("[placeholder=\"Город\"]").click();
            $$(".select__list__item").filterBy(text("Санкт-Петербург")).first().click();
            $(byText("Сохранить")).click();
        });

        step("going to internships page", () -> {
            $("[href=\"/internships/\"]").click();
        });

        step("click wanna be with you", () -> {
            $(".about__top__text .button.button.button-regular.button-normal").click();
        });

        step("pick the firstt vacancy", () -> {
            $(".results__items .wr").click();
        });

        step("share on vacancy link", () -> {
            Selenide.switchTo().window(1);
            $(".vacancy .vacancy__actions__share").click();
        });

        step("message should be ссылка скопирована", () -> {
            $(".vacancy .tooltip").shouldHave(text("Ссылка успешно скопирована!"));
        });
    }

    @Test
    @Description("Simple test")
    @DisplayName("Page should have links to social network pages")
    void socialNetworkLinksTest() {
        step("Open url 'https://job.ozon.ru/", () ->
            open("/"));

        step("close modal window", () -> {
            $(".modal__content .close").click();
        });

        step("Page menu should have correct link on vk.com", () -> {
            $$(".info__socials a").find(Condition.href("https://vk.com/ozon")).click();
        });

        step("Page menu should have correct link on fb.com", () -> {
            $$(".info__socials a").find(Condition.href("https://www.facebook.com/ozon.ru/")).click();
        });

        step("Page menu should have correct link on instagram.com", () -> {
            $$(".info__socials a").find(Condition.href("https://www.instagram.com/ozonru/")).click();
        });
    }

    @Test
    @Description("Simple test")
    @DisplayName("Page title should have header text")
    void titleTest() {
        step("Open url '/vacancy/'", () ->
            open("/vacancy/"));

        step("Page title should have text 'Вакансии компании Ozon – Работа в Ozon'", () -> {
            String expectedTitle = "Вакансии компании Ozon – Работа в Ozon";
            String actualTitle = title();

            System.out.println(actualTitle + '\n' + expectedTitle);
            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }

    @Test
    @Description("Simple test")
    @DisplayName("Page console log should not have errors")
    void consoleShouldNotHaveErrorsTest() {
        step("Open url '/vacancy/?department=Ozon%20Fintech&experience=%D0%9E%D1%82%201%20%D0%B3%D0%BE%D0%B4%D0%B0%20%D0%B4%D0%BE%203%20%D0%BB%D0%B5%D1%82&query=%D0%B0%D0%B2%D1%82%D0%BE%D0%BC%D0%B0'", () ->
            open("/vacancy/?department=Ozon%20Fintech&experience=%D0%9E%D1%82%201%20%D0%B3%D0%BE%D0%B4%D0%B0%20%D0%B4%D0%BE%203%20%D0%BB%D0%B5%D1%82&query=%D0%B0%D0%B2%D1%82%D0%BE%D0%BC%D0%B0"));

        step("Console logs should not contain text 'SEVERE'", () -> {
            String consoleLogs = DriverUtils.getConsoleLogs();
            String errorText = "SEVERE";

            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }
}
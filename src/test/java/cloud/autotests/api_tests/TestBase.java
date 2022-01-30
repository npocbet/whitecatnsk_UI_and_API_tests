package cloud.autotests.api_tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static io.qameta.allure.Allure.step;

public class TestBase {
    String productId;
    String cookie;
    String rowID;

    @BeforeAll
    static void setUp(){
        Configuration.baseUrl = "https://www.whitecatnsk.ru/";
    }

    @BeforeEach
    void firstStep(){

        step("Open main page", () -> {
            open("/");
        });

        step("Get session cookie", () -> {
            cookie = String.valueOf(webdriver().driver().getWebDriver().manage().getCookieNamed("PHPSESSID").getValue());
        });

    }


}

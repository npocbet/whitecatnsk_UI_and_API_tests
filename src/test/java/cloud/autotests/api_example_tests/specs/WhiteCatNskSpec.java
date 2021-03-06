package cloud.autotests.api_example_tests.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static cloud.autotests.api_example_tests.filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.with;

public class WhiteCatNskSpec {
    public static RequestSpecification whiteCatNskRequestSpec = with()
            .baseUri("https://www.whitecatnsk.ru/")
            .filter(customLogFilter().withCustomTemplates())
            .log().all();

    public static RequestSpecification reqresRequestSpec = with()
            .baseUri("https://reqres.in/")
            .basePath("/api")
            .filter(customLogFilter().withCustomTemplates())
            .log().all();

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)  // вместо обычного .log().all(), здесь почему-то так
            .expectStatusCode(200)
            .build();


}

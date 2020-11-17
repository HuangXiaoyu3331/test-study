package com.hxy.restassured;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.hxy.controller.SpringMockMvcController;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.response.Response;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.*;

/**
 * @author huangxy
 * @date 2020/11/13 16:06:26
 */
// use "@AutoConfigureWireMock" annotation instead of "SpringClassRule、SpringMethodRule" would work as well
//@AutoConfigureWireMock(port = 8080)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleTest {

    @ClassRule
    public static SpringClassRule springClassRule = new SpringClassRule();
    @Rule
    public SpringMethodRule springMethodRule = new SpringMethodRule();
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    @Test
    public void example1() {
        given().when().get("/lotto")
                .then().body("lotto.lottoId", equalTo(5))
                .body("lotto.winners.winnerId", hasItems(23, 54));
    }

    /**
     * You can configure Rest Assured and JsonPath to return BigDecimal's instead of float and double for Json Numbers. For example consider the following JSON document:
     */
    @Test
    public void floatAndDouble2BigDecimal() {
        // By default you validate that price is equal to 12.12 as a float like this:
        given().when().get("/price").then().body("price", is(12.12f));
        // if you don't need to pass parameters,you can omit 'given()' like this
        // get("/price").then().body("price", is(12.12f));

        // but if you like you can configure REST Assured to use a JsonConfig that returns all Json numbers as BigDecimal:
        given().
                config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL))).
                when().
                get("/price").
                then().
                body("price", is(new BigDecimal("12.12")));
    }

    @Test
    public void jsonSchemaValidationTest() {
        given().when().get("/products").then()
                .assertThat().body(matchesJsonSchemaInClasspath("schema/restassured/products-schema.json"));
    }

    @Test
    public void jsonSchemaValidationSettingsTest() {
        // given
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze()).freeze();
        // when
        get("/products").then()
                .assertThat().body(matchesJsonSchemaInClasspath("schema/restassured/products-schema.json").using(jsonSchemaFactory));

        // The fge library also allows the validation to be checked or unchecked. By default REST Assured uses checked validation
        // but if you want to change this you can supply an instance of JsonSchemaValidatorSettings to the matcher. For example:
        // TODO do not understand the meaning of the document
//        get("/products").then()
//                .assertThat().body(matchesJsonSchemaInClasspath("schema/restassured/products-schema.json")
//                .using(settings().with().checkedValidation(false)));
    }

    @Test
    public void anonymousJsonRootValidationTest() {
        get("/anonymous").then()
                .body("$", hasItem(1)).extract(); // An empty string "" would work as well
    }

    @Test
    public void jsonExpressionValidationTest() {
        get("/store").then()
                .body("store.book.findAll{it.price<10}.title", hasItems("Sayings of the Century", "Moby Dick"));

        get("/store").then().
                body("store.book.author.collect { it.length() }.sum()", greaterThan(50));
    }

    @Test
    public void getResponseTest() {
        String responseBody = get("/anonymous").asString();
        System.out.println("responseBody:" + responseBody);
        Response response = get("/anonymous");

        // get all headers
        Headers allHeaders = response.getHeaders();
        System.out.println("allHeaders:" + allHeaders);

        // Get a single header value:
        String contentType = response.getHeader("Content-Type");
        System.out.println("contentType:" + contentType);

        // Get all cookies as simple name-value pairs
        Map<String, String> allCookies = response.getCookies();
        System.out.println("allCookies:" + allCookies);

        // Get a single cookie value:
        String cookieValue = response.getCookie("cookieName");
        System.out.println("cookieValue:" + cookieValue);

        // Get status line
        String statusLine = response.getStatusLine();
        System.out.println("statusLine:" + statusLine);

        // Get status code
        int statusCode = response.getStatusCode();
        System.out.println("statusCode:" + statusCode);
    }

    @Test
    public void ParameterTest() {
        // automatically determine parameter type
        given().log().all()
                .param("pathParam", "path")
                .when()
                .post("/parameter")
                .then().log().all()
                .assertThat().statusCode(200);
        // parameters can also be set directly on the url: ..when().get("/name?firstName=John&lastName=Doe");

//        given().log().all()
//                .formParam("formParamName", "value1")
//                .queryParam("queryParamName", "value2")
//                .when()
//                .post("/parameter");

        // multi-value parameter
        List<String> values = new ArrayList<String>();
        values.add("value1");
        values.add("value2");
        given().log().all()
                .param("myList", values)
                .when()
                .post("/parameter")
                .then().log().all()
                .assertThat().statusCode(200);

        // path parameters
        // You can also specify so called path parameters in your request, e.g. post("/reserve/{hotelId}/{roomNumber}", "My Hotel", 23);
    }

}

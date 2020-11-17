package com.hxy.restassured;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.hxy.controller.SpringMockMvcController;
import io.restassured.module.spring.commons.config.AsyncConfig;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.config;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;


/**
 * @author huangxy
 * @date 2020/11/16 15:32:52
 */
public class SpringMockMvcTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

//    @Before
//    public void setup() {
//        RestAssuredMockMvc.standaloneSetup(new SpringMockMvcController());
//    }

    @Test
    public void simpleTest() {
        given().standaloneSetup(new SpringMockMvcController()).when()
                .get("/spring-mock-mvc/get").then()
                .assertThat().statusCode(200)
                .assertThat().body(equalTo("success"));
    }

    @Test
    public void asyncTest() {
        // default timeout = 1s
        given().standaloneSetup(new SpringMockMvcController()).body("a string")
                .when()
                .async().with().timeout(4, TimeUnit.SECONDS)
                .post("/spring-mock-mvc/stringBody")
                .then()
                .body(equalTo("a string"));
    }

    @Test
    public void asyncConfigTest() {
        given().standaloneSetup(new SpringMockMvcController())
                .config(config().asyncConfig(AsyncConfig.withTimeout(4, TimeUnit.SECONDS)))
                .body("async config")
                .when()
                .async().post("/spring-mock-mvc/stringBody")
                .then()
                .body(equalTo("async config"));
        // apply the config globally to apply to all requests
        // RestAssuredMockMvc.config = RestAssuredMockMvc.config().asyncConfig(withTimeout(100, TimeUnit.MILLISECONDS));
    }

}

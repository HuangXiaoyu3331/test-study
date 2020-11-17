package com.hxy.wiremock;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.hxy.controller.SampleController;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.StubImport.stubImport;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author huangxy
 * @date 2020/11/13 11:25:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WireMockRuleTest {

    public static final int port = 8080;
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(port);
    @Autowired
    private SampleController sampleController;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void bulkImportTest() {
        importStubs(stubImport()
                .stub(get("/get").willReturn(ok("get success")))
                .stub(post("/post").willReturn(ok("post success")))
                .stub(delete("/delete").willReturn(ok()))
                .stub(put("/put").willReturn(ok("put success"))));

        assertThat(sampleController.get(), is(Matchers.equalTo("get success")));
        assertThat(sampleController.post(), is(Matchers.equalTo("post success")));
        assertThat(sampleController.delete(1), is(Matchers.equalTo("delete success")));
        assertThat(sampleController.put(), is(Matchers.equalTo("put success")));
    }

    @Test
    public void mappingsFileTest() {
        assertThat(sampleController.getName(), is(Matchers.equalTo("hxy")));
    }

    @Test
    public void stubTest() {
        stubFor(get("/stubFor")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Customer-head", "hxy")
                        .withBody("stubFor mock success")));

        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/stubFor", String.class);

        assertThat(entity.getStatusCodeValue(), is(Matchers.equalTo(200)));
        assertThat(entity.getHeaders().get("X-Customer-head"), is(Matchers.hasItem("hxy")));
        assertThat(entity.getBody(), is(Matchers.equalTo("stubFor mock success")));
    }

}

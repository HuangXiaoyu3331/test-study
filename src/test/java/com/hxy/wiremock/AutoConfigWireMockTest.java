package com.hxy.wiremock;

import com.hxy.controller.SampleController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author huangxy
 * @date 2020/11/13 14:04:21
 */
@AutoConfigureWireMock(port = 8080)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AutoConfigWireMockTest {

    @Autowired
    private SampleController sampleController;

    @Test
    public void test() {
        stubFor(get("/get")
                .willReturn(ok("get success")));

        assertThat(sampleController.get(), is(Matchers.equalTo("get success")));
    }

}

package com.hxy.wiremock;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.hxy.controller.SampleController;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author huangxy
 * @date 2020/11/13 16:17:28
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringClassRuleTest {

    @ClassRule
    public static SpringClassRule springClassRule = new SpringClassRule();
    @Rule
    public SpringMethodRule springMethodRule = new SpringMethodRule();
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);
    @Autowired
    private SampleController sampleController;

    @Test
    public void test() {
        assertThat(sampleController.getName(), is(equalTo("hxy")));
    }

}

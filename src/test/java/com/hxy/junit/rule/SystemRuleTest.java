package com.hxy.junit.rule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author huangxy
 * @date 2021/08/09 23:39:26
 */
public class SystemRuleTest {

    @Rule
    public EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Before
    public void setup() {
        environmentVariables.set("name", "huangxy");
    }

    @Test
    public void getEnv() {
        assertThat(System.getenv("name"), is(equalTo("huangxy")));
    }

}

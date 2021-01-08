package com.hxy.junit.rule;

import org.junit.Rule;
import org.junit.rules.ExternalResource;

/**
 * @author huangxy
 * @date 2021/01/08 16:41:00
 */
public class ExternalResourceRuleTest {

    @Rule
    public final ExternalResource externalResource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            // code to set up a specific external resource.
        };

        @Override
        protected void after() {
            // code to tear down the external resource
        };
    };

}

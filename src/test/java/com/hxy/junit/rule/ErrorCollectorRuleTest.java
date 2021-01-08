package com.hxy.junit.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * @author huangxy
 * @date 2021/01/08 12:13:46
 */
public class ErrorCollectorRuleTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void test() {
        errorCollector.addError(new Throwable("First thing went wrong!"));
        errorCollector.addError(new Throwable("Second thing went wrong!"));
        errorCollector.checkThat("Hello World", not(containsString("ERROR!")));
        System.out.println("run finished");
    }

}

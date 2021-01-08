package com.hxy.junit.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author huangxy
 * @date 2021/01/08 17:08:17
 */
public class CustomerRuleTest {

    @Rule
    public TestMethodNameLogger testMethodNameLogger = new TestMethodNameLogger();

    @Test
    public void test() {
        System.out.println("run case");
    }

    static class TestMethodNameLogger implements TestRule {

        @Override
        public Statement apply(Statement statement, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    System.out.println("before test,class name is " + description.getClassName());
                    statement.evaluate();
                    System.out.println("after test,class name is " + description.getClassName());
                }
            };

        }
    }

}

package com.hxy.junit.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author huangxy
 * @date 2021/01/08 18:09:28
 */
public class RuleChainsTest {

    @Rule
    public RuleChain ruleChain = RuleChain.outerRule(new SimpleMsgLogger("First Rule"))
            .around(new SimpleMsgLogger("Second Rule"))
            .around(new SimpleMsgLogger("Third Rule"));

    @Test
    public void test() {
        System.out.println("run case");
    }

    static class SimpleMsgLogger implements TestRule {

        private String log;

        public SimpleMsgLogger(String log) {
            this.log = log;
        }

        @Override
        public Statement apply(Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    System.out.println("Starting:" + log);
                    base.evaluate();
                    System.out.println("Finishing:" + log);
                }
            };
        }
    }

}

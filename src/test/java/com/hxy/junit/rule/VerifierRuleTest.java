package com.hxy.junit.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Verifier;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertFalse;

/**
 * @author huangxy
 * @date 2021/01/08 15:24:34
 */
public class VerifierRuleTest {

    private List messageLog = new ArrayList<>();

    @Rule
    public Verifier verifier = new Verifier() {
        @Override
        protected void verify() throws Throwable {
            assertFalse("Message Log is not Empty!", messageLog.isEmpty());
        }
    };

    @Test
    public void successTest() {
        messageLog.add("I love java");
    }

    @Test
    public void failTest() {
        System.out.println("I don't like java");
    }

}

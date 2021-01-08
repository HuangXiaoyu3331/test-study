package com.hxy.junit.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.isA;

/**
 * @author huangxy
 * @date 2021/01/08 11:58:38
 */
public class ExpectedExceptionRuleTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectCause(isA(NullPointerException.class));
        thrown.expectMessage("This is illegal");

        throw new IllegalArgumentException("This is illegal", new NullPointerException());
    }

}

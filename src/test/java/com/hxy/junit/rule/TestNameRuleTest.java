package com.hxy.junit.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author huangxy
 * @date 2021/01/08 12:02:19
 */
public class TestNameRuleTest {

    @Rule
    public TestName name = new TestName();

    @Test
    public void test() {
        System.out.println("method name = " + name.getMethodName());
        assertEquals("test", name.getMethodName());
    }

}

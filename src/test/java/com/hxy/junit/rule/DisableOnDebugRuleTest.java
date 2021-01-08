package com.hxy.junit.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.Timeout;

import java.util.concurrent.TimeUnit;

/**
 * @author huangxy
 * @date 2021/01/08 16:15:11
 */
public class DisableOnDebugRuleTest {

    @Rule
    public DisableOnDebug disableTimeout = new DisableOnDebug(Timeout.seconds(5));

    @Test
    public void test() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
    }

}

package com.hxy.junit.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.concurrent.TimeUnit;

/**
 * @author huangxy
 * @date 2021/01/08 12:03:11
 */
public class TimeoutRuleTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    @Test
    public void timeout() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void onTime() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
    }

}

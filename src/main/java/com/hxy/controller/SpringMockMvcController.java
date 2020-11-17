package com.hxy.controller;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * spring mock mvc test controller
 *
 * @author huangxy
 * @date 2020/11/16 16:45:29
 */
@RestController
@RequestMapping("/spring-mock-mvc")
public class SpringMockMvcController {

    @GetMapping("/get")
    public String get() {
        return "success";
    }

    @PostMapping("/stringBody")
    public Callable<String> stringBody(@RequestBody String body) {
        return () -> {
            TimeUnit.SECONDS.sleep(3);
            return body;
        };
    }

}

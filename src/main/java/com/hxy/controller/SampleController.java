package com.hxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * simple controller,provide simple GET,PUT,POST,DELETE method
 *
 * @author huangxy
 * @date 2020/11/08 11:21:55
 */
@RestController
public class SampleController {

    public static final String BASE = "http://localhost:8080";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/get")
    public String get() {
        return restTemplate.getForEntity(BASE + "/get", String.class).getBody();
    }

    @GetMapping("/post")
    public String post() {
        return restTemplate.postForEntity(BASE + "/post", null, String.class).getBody();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        restTemplate.delete(BASE + "/delete", Collections.singletonMap("id", id));
        return "delete success";
    }

    @GetMapping("/put")
    public String put() {
        restTemplate.put(BASE + "/put", null, String.class);
        return "put success";
    }

    @GetMapping("/name")
    public String getName() {
        return restTemplate.getForEntity(BASE + "/name", String.class).getBody();
    }

}

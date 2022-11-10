package com.example.controller;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @ClassName TestController
 * @Author PanWZ
 * @Data 2022/11/7 - 10:55
 * @Version: 1.8
 */
@RestController
public class TestController {

    @Resource
    RestTemplate restTemplate;

    @GetMapping("/test")
    public String test(String sourceAndEnd) {
        String url = "http://www.baidu.com";
        String body = sourceAndEnd;
        HttpEntity<String> entity = new HttpEntity<>(body, null);
        String s = restTemplate.postForObject(url, entity, String.class);
        System.out.println(s);
        return s;
    }
}

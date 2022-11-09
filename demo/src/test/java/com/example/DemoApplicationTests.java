package com.example;

import net.sf.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Test
    public void test() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/test";
        // 2.请求头 & 请求体
        String body = "0,22";
        HttpEntity<String> entity = new HttpEntity<>(body, null);
        String s = restTemplate.postForObject(url,  entity, String.class);
        System.out.println(s);
//        List<String> strings = Arrays.asList("1", "2", "3");
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.addAll(strings);
//        System.out.println(jsonArray);

    }

}

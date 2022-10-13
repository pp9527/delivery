package com.example;

import com.alibaba.fastjson.JSONObject;
import com.example.utils.GuideRoutePlanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    @Test
    public void test() {
        System.out.println(GuideRoutePlanUtils.getDistanceOfPlanFromGuide("W1", "U1"));
    }
}

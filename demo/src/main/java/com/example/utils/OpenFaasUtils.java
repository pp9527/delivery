package com.example.utils;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @ClassName OpenfaasUtil
 * @Author PanWZ
 * @Data 2022/11/8 - 17:04
 * @Version: 1.8
 */
public class OpenFaasUtils {

//    W1,U1,1,1,1000,1265 897 2165 4444 3730
    public static String getShortestTimePath(String startStation, String endStation, int droneId
            , int carId, int weight, String carToUserDistance) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://www.baidu.com";
        //请求体
        String body = startStation + "," + endStation + "," + droneId + "," + carId + ","
                + weight + "," + carToUserDistance;
        HttpEntity<String> entity = new HttpEntity<>(body, null);
        String s = restTemplate.postForObject(url, entity, String.class);
        System.out.println(s);
        return s;
    }
}

package com.example.utils;

import com.example.controller.AjaxController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName OpenfaasUtil
 * @Author PanWZ
 * @Data 2022/11/8 - 17:04
 * @Version: 1.8
 */
public class OpenFaasUtils {

    private static final Logger log = LoggerFactory.getLogger(OpenFaasUtils.class);

    /* ServiceComposition */

    /**
     * @Description: 请求距离最短路径规划服务
     *               测试用例 ：0,22
     * @author pwz
     * @date 2022/11/10 15:36
     */
    public static String getShortestDistancePath1(int startStation, int endStation) {
        //        测试数据
//        return "W1->D7->D16->D15->C6,5975";
        log.info("*******openfaas request start!*******");
        log.info("request -> openfaas: ");
        RestTemplate restTemplate = new RestTemplate();
        //        openfaas请求url
        String url = "";
        String body = startStation + "," + endStation;
        HttpEntity<String> entity = new HttpEntity<>(body, null);
        log.info("request body: " + body);
        String s = restTemplate.postForObject(url, entity, String.class);
        log.info("request successful! response body: " + s);
        log.info("*******openfaas request end!*******");
        return s;
    }

    /**
     * @Description: 请求时间最短路径规划服务
     *               测试用例 ：W1,U1,1,1,1000,1265 897 2165 4444 3730
     * @author pwz
     * @date 2022/11/10 15:36
     */
    public static String getShortestTimePath1(String startStation, String endStation, int droneId
            , int carId, int weight, String carToUserDistance) {
        //        测试数据
//        return "W1,D7,D13,C3,863,199215";
        log.info("*******openfaas request start!*******");
        log.info("request -> openfaas: ");
        RestTemplate restTemplate = new RestTemplate();
        //        openfaas请求url
        String url = "";
        String body = startStation + "," + endStation + "," + droneId + "," + carId + ","
                + weight + "," + carToUserDistance;
        HttpEntity<String> entity = new HttpEntity<>(body, null);
        log.info("request body: " + body);
        String s = restTemplate.postForObject(url, entity, String.class);
        log.info("request successful! response body: " + s);
        log.info("*******openfaas request end!*******");
        return s;
    }

    /**
     * @Description: 请求能耗最小路径规划服务
     *               测试用例 ：W1,U1,0,0,1000,1265 897 2165 4444 3730
     * @author pwz
     * @date 2022/11/10 15:36
     */
    public static String getShortestEnergyPath1(String startStation, String endStation, int droneId
            , int carId, int weight, String carToUserDistance) {
        //        测试数据
//        return "W1,D7,C11,1245,154557";
        log.info("*******openfaas request start!*******");
        log.info("request -> openfaas: ");
        RestTemplate restTemplate = new RestTemplate();
        //        openfaas请求url
        String url = "";
        String body = startStation + "," + endStation + "," + droneId + "," + carId + ","
                + weight + "," + carToUserDistance;
        HttpEntity<String> entity = new HttpEntity<>(body, null);
        log.info("request body: " + body);
        String s = restTemplate.postForObject(url, entity, String.class);
        log.info("request successful! response body: " + s);
        log.info("*******openfaas request end!*******");
        return s;
    }

    /**
     * @Description: 请求时间约束下能耗最小路径规划服务
     *               测试用例 ：W1,U1,0,0,30,1000,1265 897 2165 4444 3730
     * @author pwz
     * @date 2022/11/10 15:36
     */
    public static String getShortestEnergyInTimePath1(String startStation, String endStation, int droneId
            , int carId, int deadline , int weight, String carToUserDistance) {
        //        测试数据
//        return "W1,D7,D13,C3,1660,160642";
        log.info("*******openfaas request start!*******");
        log.info("request -> openfaas: ");
        RestTemplate restTemplate = new RestTemplate();
        //        openfaas请求url
        String url = "";
        String body = startStation + "," + endStation + "," + droneId + "," + carId + ","
                + deadline + "," + weight + "," + carToUserDistance;
        HttpEntity<String> entity = new HttpEntity<>(body, null);
        log.info("request body: " + body);
        String s = restTemplate.postForObject(url, entity, String.class);
        log.info("request successful! response body: " + s);
        log.info("*******openfaas request end!*******");
        return s;
    }

    /* ResourceAllocation */

    /**
     * @Description: 请求距离最短路径规划服务
     *               测试用例 ：0,22
     * @author pwz
     * @date 2022/11/10 15:36
     */
    public static String getShortestDistancePath2(int startStation, int endStation) {
        //        测试数据
//        return "W1->D7->D16->D15->C6,5975";
        log.info("*******openfaas request start!*******");
        log.info("request -> openfaas: ");
        RestTemplate restTemplate = new RestTemplate();
        //        openfaas请求url
        String url = "";
        String body = startStation + "," + endStation;
        HttpEntity<String> entity = new HttpEntity<>(body, null);
        log.info("request body: " + body);
        String s = restTemplate.postForObject(url, entity, String.class);
        log.info("request successful! response body: " + s);
        log.info("*******openfaas request end!*******");
        return s;
    }

    /**
     * @Description: 请求时间最短路径规划服务
     *               测试用例 ：W1,U1,1,1,1000,1265 897 2165 4444 3730
     * @author pwz
     * @date 2022/11/10 15:36
     */
    public static String getShortestTimePath2(String startStation, String endStation, int droneId
            , int carId, int weight, String carToUserDistance) {
        //        测试数据
//        return "W1,D7,D13,C3,863,199215";
        log.info("*******openfaas request start!*******");
        log.info("request -> openfaas: ");
        RestTemplate restTemplate = new RestTemplate();
        //        openfaas请求url
        String url = "";
        String body = startStation + "," + endStation + "," + droneId + "," + carId + ","
                + weight + "," + carToUserDistance;
        HttpEntity<String> entity = new HttpEntity<>(body, null);
        log.info("request body: " + body);
        String s = restTemplate.postForObject(url, entity, String.class);
        log.info("request successful! response body: " + s);
        log.info("*******openfaas request end!*******");
        return s;
    }

    /**
     * @Description: 请求能耗最小路径规划服务
     *               测试用例 ：W1,U1,0,0,1000,1265 897 2165 4444 3730
     * @author pwz
     * @date 2022/11/10 15:36
     */
    public static String getShortestEnergyPath2(String startStation, String endStation, int droneId
            , int carId, int weight, String carToUserDistance) {
        //        测试数据
//        return "W1,D7,C11,1245,154557";
        log.info("*******openfaas request start!*******");
        log.info("request -> openfaas: ");
        RestTemplate restTemplate = new RestTemplate();
        //        openfaas请求url
        String url = "";
        String body = startStation + "," + endStation + "," + droneId + "," + carId + ","
                + weight + "," + carToUserDistance;
        HttpEntity<String> entity = new HttpEntity<>(body, null);
        log.info("request body: " + body);
        String s = restTemplate.postForObject(url, entity, String.class);
        log.info("request successful! response body: " + s);
        log.info("*******openfaas request end!*******");
        return s;
    }

    /**
     * @Description: 请求时间约束下能耗最小路径规划服务
     *               测试用例 ：W1,U1,0,0,30,1000,1265 897 2165 4444 3730
     * @author pwz
     * @date 2022/11/10 15:36
     */
    public static String getShortestEnergyInTimePath2(String startStation, String endStation, int droneId
            , int carId, int deadline , int weight, String carToUserDistance) {
        //        测试数据
//        return "W1,D7,D13,C3,1660,160642";
        log.info("*******openfaas request start!*******");
        log.info("request -> openfaas: ");
        RestTemplate restTemplate = new RestTemplate();
        //        openfaas请求url
        String url = "";
        String body = startStation + "," + endStation + "," + droneId + "," + carId + ","
                + deadline + "," + weight + "," + carToUserDistance;
        HttpEntity<String> entity = new HttpEntity<>(body, null);
        log.info("request body: " + body);
        String s = restTemplate.postForObject(url, entity, String.class);
        log.info("request successful! response body: " + s);
        log.info("*******openfaas request end!*******");
        return s;
    }
}

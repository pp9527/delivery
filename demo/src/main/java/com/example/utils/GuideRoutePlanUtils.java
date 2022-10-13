package com.example.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.service.CarStationService;
import com.example.service.CustomerService;
import com.example.service.DroneStationService;
import net.sf.json.JSON;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: pwz
 * @create: 2022/10/13 15:15
 * @Description:
 * @FileName: GuideRoutePlanUtil
 */
@Component
public class GuideRoutePlanUtils {

    public static GuideRoutePlanUtils guideRoutePlanUtils;

    @PostConstruct
    public void init() {
        guideRoutePlanUtils = this;
    }

    @Resource
    RestTemplate restTemplate;

    @Resource
    CustomerService customerService;

    @Resource
    DroneStationService droneStationService;

    @Resource
    CarStationService carStationService;

    /**
     * @Description: 解析高德无人车路径规划结果，返回路径长度/m
     * @author pwz
     * @date 2022/10/13 15:54
     * @return int
     */
    public static int getDistanceOfPlanFromGuide(String startStation, String consignee) {
        List<Double> origin = guideRoutePlanUtils.carStationService.getLocationByName(startStation);
        List<Double> des = guideRoutePlanUtils.customerService.getLocationByName(consignee);

        String url = "https://restapi.amap.com/v3/direction/driving?origin=" +
                origin.get(0) + "," + origin.get(1) + "&destination=" +
                des.get(0) + "," + des.get(1) + "&extensions=base&output=json&" +
                "key=db57812ceb3ba9d7f21906ff89e1b933";

//        String forObject = restTemplate.getForObject("https://restapi.amap.com/v3/direction/driving?origin=116.481028,39.989643&destination=116.465302,40.004717&extensions=all&output=json&key=db57812ceb3ba9d7f21906ff89e1b933", String.class);
        String forObject = guideRoutePlanUtils.restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        JSONObject route = (JSONObject) jsonObject.get("route");
        List paths = (List) route.get("paths");
        JSONObject path = (JSONObject) paths.get(0);
        int distance = Integer.parseInt((String) path.get("distance"));
        return distance;
    }
}
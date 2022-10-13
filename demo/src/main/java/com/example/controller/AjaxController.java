package com.example.controller;


import com.example.service.CarToCustomerService;
import com.example.service.CustomerService;
import com.example.utils.RoutePlanning;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.awt.print.Book;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/26 11:24
 * @Description:
 * @FileName: AjaxController
 */
@Controller
@RequestMapping("/ajax")
public class AjaxController {

    @Resource
    CustomerService customerService;

    @ResponseBody
    @PostMapping("/generate")
    public List<List<Double>> generatePath(String model, String startStation,
                                           String consignee, String objective) {
        List<List<Double>> shortestPaths = RoutePlanning.
                selectStrategyByObjective(model, startStation, consignee, objective, 2, 2);
        List<Double> desLocation = customerService.getLocationByName(consignee);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(shortestPaths);
        jsonArray.add(desLocation);
        return jsonArray;
    }

    @ResponseBody
    @PostMapping("/getRoute")
    public List<List<Double>> getRoute(String model, String startStation
            , int uavType, int ugvType, String consignee, String objective) {
        List<List<Double>> shortestPaths = RoutePlanning.
                selectStrategyByObjective(model, startStation, consignee, objective, uavType, ugvType);
        List<Double> desLocation = customerService.getLocationByName(consignee);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(shortestPaths);
        jsonArray.add(desLocation);
        return jsonArray;
    }

}
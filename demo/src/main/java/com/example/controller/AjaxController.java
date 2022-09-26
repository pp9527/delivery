package com.example.controller;


import com.example.service.CarToCustomerService;
import com.example.service.CustomerService;
import com.example.utils.RoutePlanning;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    CarToCustomerService carToCustomerService;

    @Resource
    CustomerService customerService;

    @ResponseBody
    @PostMapping("/generate")
    public List<List<Double>> generatePath(String model, String startStation,
                                           String consignee, String objective) {
        List<List<Double>> shortestPaths = RoutePlanning.getShortestPaths(startStation, consignee);
        List<Double> desLocation = customerService.getLocationByName(consignee);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(shortestPaths);
        jsonArray.add(desLocation);
        return jsonArray;
    }
}
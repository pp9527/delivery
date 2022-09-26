package com.example.controller;


import com.example.service.CarToCustomerService;
import com.example.utils.RoutePlanning;
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

    @ResponseBody
    @PostMapping("/generate")
    public List<List<Double>> generatePath(String model, String startStation,
                                           String consignee, String objective) {

        List<List<Double>> shortestPaths = RoutePlanning.getShortestPaths(startStation, consignee);

        return shortestPaths;
    }
}
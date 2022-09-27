package com.example.controller;

import com.example.bean.Order;
import com.example.service.CustomerService;
import com.example.service.OrderService;
import com.example.service.PathService;
import com.example.utils.RoutePlanning;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/21 11:48
 * @Description:
 * @FileName: OrderController
 */
@Controller
public class OrderController {

    @Resource
    OrderService orderService;

    @Resource
    PathService pathService;

    @Resource
    CustomerService customerService;

    @RequestMapping("/insertOrder")
    public String insertOrder(Order order, String objective) {

//        1、插入一条新订单到order_record表
        List<Double> locationByName = customerService.getLocationByName(order.getConsignee());
        order.setDesLongitude(locationByName.get(0));
        order.setDesLatitude(locationByName.get(1));
        order.setPrivacyLongitude(locationByName.get(0));
        order.setPrivacyLatitude(locationByName.get(1));
        order.setDeadline(new Date());
        orderService.save(order);
//        2、根据订单规划路径，结果插入path表
        List<String> stationName = RoutePlanning.getShortestStationName(order.getStartStation(),
                order.getConsignee());
        pathService.insertPaths(stationName, order.getOrderId());
//        3、发送订单id，重定向到main/id
        return "redirect:/orders";
    }


}
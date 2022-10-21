package com.example.controller;

import com.example.bean.Order;
import com.example.service.CustomerService;
import com.example.service.OrderService;
import com.example.service.PathService;
import com.example.utils.RoutePlanning;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
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
    public String insertOrder(Order order, int uavType, int ugvType, String objective) {
//        1、根据订单信息规划路径, 插入path表
        List<String> route = RoutePlanning.getRouteByObjective(order, objective, uavType, ugvType);
//        System.out.println(route);
        pathService.insertPaths(route, order.getOrderId());
//        2、根据OrderId查询路径转化为string，填充order.route属性
        List<String> stations = pathService.getPathStationsByOrderId(order.getOrderId());
        String string = RoutePlanning.pathListToString(stations, order.getConsignee());
        System.out.println("最终所选方案为: " + string);
//        String string = RoutePlanning.pathListToString(route, order.getConsignee());
        order.setRoute(string);
//        3、根据用户查询用户坐标，填充order相关属性
        List<Double> locationByName = customerService.getLocationByName(order.getConsignee());
        order.setDesLongitude(locationByName.get(0));
        order.setDesLatitude(locationByName.get(1));
        order.setPrivacyLongitude(locationByName.get(0));
        order.setPrivacyLatitude(locationByName.get(1));
//        4、插入订单表order
//        System.out.println(order);
        orderService.save(order);
        return "redirect:/orders";
    }


}
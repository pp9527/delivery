package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bean.*;
import com.example.service.*;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/1 16:14
 * @Description:
 * @FileName: IndexController
 */
@Controller
public class LoginController {

    @Resource
    WareService wareService;

    @Resource
    CarStationService carStationService;

    @Resource
    OrderService orderService;

    @Resource
    DroneStationService droneStationService;

    @Resource
    PathService pathService;

    @Resource
    StationNetMapService stationNetMapService;

    @Resource
    CarToCustomerService carToCustomerService;

    @Resource
    CustomerService customerService;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String main() {
        return "redirect:/main.html";
    }


    @GetMapping("/main.html")
    public String mainPage(Model model) {
//        Order order = orderService.getById(id);
//        查询当前活跃订单
        List<Order> activeOrders = orderService.selectActiveOrders();
//        List<List<Double>> path = pathService.selectPathByOrderId(order.getOrderId());

//        查询所有站点名称(drone_station表+car_station表id顺序)
        List<String> droneStationNames = droneStationService.getNames();
        List<String> carStationNames = carStationService.getNames();
        JSONArray stationName = new JSONArray();
        stationName.addAll(droneStationNames);
        stationName.addAll(carStationNames);

//        查询所有站点坐标(drone_station表+car_station表id顺序)
        List<List<Double>> droneStationLongitudesAndLatitudes = droneStationService.getLongitudesAndLatitudes();
        List<List<Double>> carStationLongitudesAndLatitudes = carStationService.getLongitudesAndLatitudes();
        JSONArray stationLongitudesAndLatitude = new JSONArray();
        stationLongitudesAndLatitude.addAll(droneStationLongitudesAndLatitudes);
        stationLongitudesAndLatitude.addAll(carStationLongitudesAndLatitudes);

//        查询各站点之间的路径(t_map表id顺序)
        List<List<List<Double>>> path = stationNetMapService.getAllStationPath();

//        查询用户坐标


//        查询无人车站点到用户的路径


        model.addAttribute("activeOrders", activeOrders);
        model.addAttribute("stationNames", stationName);
        model.addAttribute("stationLongitudesAndLatitudes", stationLongitudesAndLatitude);
        model.addAttribute("pathLists", path);

//        System.out.println(stationLongitudesAndLatitude);
//        System.out.println(stationName);

        return "main";
    }

}
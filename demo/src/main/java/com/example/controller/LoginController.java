package com.example.controller;

import com.example.bean.Order;
import com.example.service.*;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
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
        List<Order> all = orderService.getAll();
        for (Order order : all) {
            List<String> lastAndNext = orderService.getLastAndNext(order.getRoute());
            order.setLastStation(lastAndNext.get(0));
            order.setNextStation(lastAndNext.get(1));
        }
//        Order order = orderService.getById(id);
//        查询当前活跃订单
        List<Order> activeOrders = orderService.getActiveOrders();
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

//        查询用户坐标和名称
        List<String> customerNames = customerService.getNames();
        List<List<Double>> customerLongitudesAndLatitudes = customerService.getLongitudesAndLatitudes();

//        查询无人车站点到用户的路径
        List<List<List<Double>>> allCarToCustomerPath = carToCustomerService.getAllCarToCustomerPath();

        model.addAttribute("allOrders", all);
        model.addAttribute("activeOrdersNum", activeOrders.size());
        model.addAttribute("stationNames", stationName);
        model.addAttribute("stationLongitudesAndLatitudes", stationLongitudesAndLatitude);
        model.addAttribute("pathLists", path);
        model.addAttribute("customerNames", customerNames);
        model.addAttribute("customerLongitudesAndLatitudes", customerLongitudesAndLatitudes);
        model.addAttribute("allCarToCustomerPath", allCarToCustomerPath);

        return "main";
    }



}
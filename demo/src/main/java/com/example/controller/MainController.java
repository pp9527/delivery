package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bean.Order;
import com.example.service.OrderService;
import com.example.service.PathService;
import com.example.service.WareService;
import com.example.utils.Graph;
import com.example.utils.PageUtils;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/3 15:29
 * @Description:
 * @FileName: MainController
 */
@Controller
public class MainController {

    @Resource
    OrderService orderService;

    @Resource
    WareService wareService;

    @Resource
    PathService pathService;

    @GetMapping("/orders")
    public String toOrders(@RequestParam(value = "pn", defaultValue = "1")Integer pn, Model model) {
//        List<Order> list = orderService.list();
//        分页查询
        Page<Order> orderPage = new Page<>(pn, 12);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        Page<Order> page = orderService.page(orderPage, wrapper);
        int[] pageNav = PageUtils.pageNav(pn, (int) page.getPages(), 5);
        model.addAttribute("orders", page);
        model.addAttribute("navNums", pageNav);
        return "orders";
    }


    @GetMapping(value = "/main/{id}")
    public String getMapById(@PathVariable("id")Integer id, Model model) {

//        查询活跃订单
        List<Order> activeOrders = orderService.getActiveOrders();
        model.addAttribute("activeOrders", activeOrders);

//        根据订单id查询订单路径
        Order order = orderService.getById(id);
        List<List<Double>> path = pathService.getPathByOrderId(order.getOrderId());
        model.addAttribute("pathLists", path);

//        根据订单id查询路线站点名和坐标
        JSONArray stationInfo = new JSONArray();
//        desLocation.add(order.getPrivacyLongitude());
//        desLocation.add(order.getPrivacyLatitude());
//        model.addAttribute("desLocation", desLocation);

        List<String> stations = pathService.getPathStationsByOrderId(order.getOrderId());
        stations.add(order.getConsignee());
        List<List<Double>> stationLocations = Graph.stationNamesToLocations(stations);
        stationInfo.add(stations);
        stationInfo.add(stationLocations);
        model.addAttribute("stationInfo", stationInfo);
        return "active_order";
    }

    @GetMapping("/safety")
    public String toSafety(Model model) {

        //查询表中最大的id
        int maxId = orderService.getMaxId();

        //        查询当前活跃订单
        List<Order> activeOrders = orderService.getActiveOrders();
        model.addAttribute("activeOrders", activeOrders);

        //        根据订单id查询订单路径
        Order order = orderService.getById(maxId);
        List<List<Double>> path = pathService.getPathByOrderId(order.getOrderId());
        model.addAttribute("pathLists", path);

//        根据订单id查询指定订单终点坐标
        JSONArray desLocation = new JSONArray();
        desLocation.add(order.getPrivacyLongitude());
        desLocation.add(order.getPrivacyLatitude());
        model.addAttribute("desLocation", desLocation);
        return "safe_order";
    }

    @GetMapping("/ai")
    public String toAi(Model model) {
        List<Order> activeOrders = orderService.getActiveOrders();
        model.addAttribute("activeOrders", activeOrders);
        return "ai_page";
    }

}
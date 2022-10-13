package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bean.Order;
import com.example.service.OrderService;
import com.example.service.PathService;
import com.example.service.WareService;
import com.example.service.impl.OrderServiceImpl;
import com.example.utils.PageUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
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


    @RequestMapping(value = "/main/{id}", method = RequestMethod.GET)
    public String getMapById(@PathVariable("id")Integer id, Model model) {

//        查询活跃订单
        List<Order> activeOrders = orderService.getActiveOrders();
        model.addAttribute("activeOrders", activeOrders);

//        根据订单id查询订单路径
        Order order = orderService.getById(id);
        List<List<Double>> path = pathService.getPathByOrderId(order.getOrderId());
        model.addAttribute("pathLists", path);

//        根据订单id查询指定订单终点坐标
        JSONArray desLocation = new JSONArray();
        desLocation.add(order.getPrivacyLongitude());
        desLocation.add(order.getPrivacyLatitude());
        model.addAttribute("desLocation", desLocation);
        return "active_order";
    }

}
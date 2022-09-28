package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bean.Order;
import com.example.service.OrderService;
import com.example.service.PathService;
import com.example.utils.SecurityAlgorithm;
import net.sf.json.JSONArray;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/23 19:35
 * @Description:
 * @FileName: SafetyController
 */
@Controller
public class SafetyController {

    @Resource
    OrderService orderService;

    @Resource
    PathService pathService;


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

    @GetMapping(value = "/safe/{id}")
    public String safeOrder(@PathVariable("id")Integer id,
                            @RequestParam(value = "type")int type) {
        Order order = orderService.getById(id);
        double[] location = {order.getDesLongitude(), order.getDesLatitude()};
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        if (type == 0) {
            // 关闭隐私保护
            order.setPrivacyLongitude(location[0]);
            order.setPrivacyLatitude(location[1]);
            order.setPrivacyStatus(false);
        } else {
            double[] enLocation = null; // 扰动后的坐标
            if (type == 1) {
                //使用SecurityAlgorithm.geoDp()方法加扰动
                enLocation = SecurityAlgorithm.geoDp(location);
            } else if (type == 2) {
                //其他方法
            } else if (type == 3) {
                //其他方法
            }
            order.setPrivacyLongitude(enLocation[0]);
            order.setPrivacyLatitude(enLocation[1]);
            order.setPrivacyStatus(true);
        }
        orderService.update(order, updateWrapper);
        return "redirect:/safety?id=" + id;
    }
}
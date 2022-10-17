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

    @GetMapping(value = "/safe/{id}")
    public String safeOrder(@PathVariable("id")Integer id,
                            @RequestParam(value = "type", required = false, defaultValue = "0")int type) {
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
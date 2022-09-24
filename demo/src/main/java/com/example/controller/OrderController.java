package com.example.controller;

import com.example.service.PathService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author: pwz
 * @create: 2022/9/21 11:48
 * @Description:
 * @FileName: OrderController
 */
@Controller
public class OrderController {


    @Resource
    PathService pathService;


    @RequestMapping("/test")
    public String test() {
//        1、插入一条新订单到order_record表
//        2、根据订单规划路径，结果插入path表  pathService.routePlanning()
//        3、发送订单id，重定向到main/id
        int id = 3;
        return "redirect:/main/" + id;
    }


}
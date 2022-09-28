package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bean.Order;

import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/13 12:44
 * @Description:
 * @FileName: OrderService
 */
public interface OrderService extends IService<Order> {

    List<Order> getActiveOrders();

    int getMaxId();

}

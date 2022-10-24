package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bean.Order;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/13 12:44
 * @Description:
 * @FileName: OrderService
 */
public interface OrderService extends IService<Order> {

    @Cacheable(value = "order")
    List<Order> getActiveOrders();

    int getMaxId();

    @CacheEvict(value = "order", allEntries = true)
    boolean save(Order order);
}

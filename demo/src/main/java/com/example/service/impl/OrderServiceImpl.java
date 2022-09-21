package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bean.Order;
import com.example.mapper.OrderMapper;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/13 12:45
 * @Description:
 * @FileName: OrderServiceImpl
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

    @Resource
    OrderMapper orderMapper;

    @Override
    public List<Order> selectActiveOrders() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("status", "select status from order_record where status != 3");
        List<Order> list = orderMapper.selectList(queryWrapper);
//        System.out.println(list.size());
        return list;
    }
}
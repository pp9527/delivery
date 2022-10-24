package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.bean.Order;
import com.example.mapper.OrderMapper;
import com.example.service.OrderService;
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
    public List<Order> getActiveOrders() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("status", "select status from order_record where status != 3");
        queryWrapper.orderByDesc("id");
        List<Order> list = orderMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public int getMaxId() {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id").last("limit 1");
        Order one = orderMapper.selectOne(wrapper);
        return one.getId();
    }

    @Override
    public boolean save(Order order) {
        return SqlHelper.retBool(this.getBaseMapper().insert(order));
    }


}
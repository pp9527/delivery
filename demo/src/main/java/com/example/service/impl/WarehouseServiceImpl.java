package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bean.Warehouse;
import com.example.mapper.WarehouseMapper;
import com.example.service.WareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: pwz
 * @create: 2022/9/15 18:59
 * @Description:
 * @FileName: WarehouseServiceImpl
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse>
        implements WareService {

    @Resource
    WarehouseMapper warehouseMapper;

    @Override
    public Warehouse selectById(int id) {
        Warehouse warehouse = warehouseMapper.selectById(id);
        return warehouse;
    }
}
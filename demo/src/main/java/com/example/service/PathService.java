package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bean.Path;

import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/16 15:39
 * @Description:
 * @FileName: PathService
 */
public interface PathService extends IService<Path>{

    List<List<Double>> getPathByOrderId(int orderId);

    void insertPaths(List<String> stationNames, int orderId);
}

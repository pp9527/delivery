package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bean.CarStation;
import com.example.bean.DroneStation;
import com.example.bean.Path;
import com.example.mapper.CarStationMapper;
import com.example.mapper.DroneStationMapper;
import com.example.mapper.PathMapper;
import com.example.service.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/16 15:39
 * @Description:
 * @FileName: PathServiceImpl
 */
@Service
public class PathServiceImpl extends ServiceImpl<PathMapper, Path> implements PathService {


    @Resource
    PathMapper pathMapper;

    @Resource
    DroneStationMapper droneStationMapper;

    @Resource
    CarStationMapper carStationMapper;

    @Override
    public List<List<Double>> selectPathByOrderId(int orderId) {

        QueryWrapper<Path> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId).orderByAsc("station_number");
        List<Path> paths = pathMapper.selectList(queryWrapper);
//        System.out.println(paths);
        List<List<Double>> pathLists = new ArrayList<>();
        for (Path path : paths) {
            double longitude, latitude;
            if (path.getDid() != 0) {
                longitude = droneStationMapper.selectById(path.getDid()).getLongitude();
                latitude = droneStationMapper.selectById(path.getDid()).getLatitude();
            } else {
                longitude = carStationMapper.selectById(path.getCid()).getLongitude();
                latitude = carStationMapper.selectById(path.getCid()).getLatitude();
            }
            ArrayList<Double> list = new ArrayList<>();
            list.add(longitude);
            list.add(latitude);
            pathLists.add(list);
        }
        return pathLists;
    }
}
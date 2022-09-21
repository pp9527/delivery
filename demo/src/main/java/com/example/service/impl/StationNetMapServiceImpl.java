package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bean.CarStation;
import com.example.bean.DroneStation;
import com.example.bean.StationNetMap;
import com.example.mapper.CarStationMapper;
import com.example.mapper.DroneStationMapper;
import com.example.mapper.StationNetMapMapper;
import com.example.service.StationNetMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/19 17:01
 * @Description:
 * @FileName: Station
 */
@Service
public class StationNetMapServiceImpl extends ServiceImpl<StationNetMapMapper, StationNetMap>
        implements StationNetMapService {

    @Resource
    StationNetMapMapper stationNetMapMapper;

    @Resource
    DroneStationMapper droneStationMapper;

    @Resource
    CarStationMapper carStationMapper;

    /**
     * @Description: 获取地图的路径集合，用于前台展示
     * @author pwz
     * @date 2022/9/20 11:30
     * @return java.util.List<java.util.List<java.util.List<java.lang.Double>>>
     */
    @Override
    public List<List<List<Double>>> getAllStationPath() {

        List<StationNetMap> stationNetMaps = stationNetMapMapper.selectList(null);
//        System.out.println(stationNetMaps);
        List<List<List<Double>>> allPath = new ArrayList<>();

        for (StationNetMap stationNetMap : stationNetMaps) {
            List<List<Double>> onePath = new ArrayList<>();
            int start = stationNetMap.getStart();
            double longitude, latitude;
            DroneStation droneStation = droneStationMapper.selectById(start);
            longitude = droneStation.getLongitude();
            latitude = droneStation.getLatitude();
            ArrayList<Double> startStation = new ArrayList<>();
            startStation.add(longitude);
            startStation.add(latitude);
            onePath.add(startStation);
            if (stationNetMap.getEndDid() != 0) {
                DroneStation droneStation1 = droneStationMapper.selectById(stationNetMap.getEndDid());
                longitude = droneStation1.getLongitude();
                latitude = droneStation1.getLatitude();
            } else {
                CarStation carStation = carStationMapper.selectById(stationNetMap.getEndCid());
                longitude = carStation.getLongitude();
                latitude = carStation.getLatitude();
            }
            ArrayList<Double> endStation = new ArrayList<>();
            endStation.add(longitude);
            endStation.add(latitude);
            onePath.add(endStation);
            allPath.add(onePath);
        }

        return allPath;
    }

    /**
     * @Description: 获取地图的逻辑信息，用于路径规划
     * @author pwz
     * @date 2022/9/20 11:31
     * @return java.util.List<java.util.List<com.example.bean.StationNetMap>>
     */
    @Override
    public List<List<StationNetMap>> getMapData() {
        List<List<StationNetMap>> pathMap = new ArrayList<>();

        QueryWrapper<StationNetMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("start");
        List<StationNetMap> list = stationNetMapMapper.selectList(queryWrapper);
        int start = list.get(0).getStart();
        List<StationNetMap> indexList = new ArrayList<>();
        for (StationNetMap stationNetMap : list) {
            if (start != stationNetMap.getStart()) {
                pathMap.add(indexList);
                start = stationNetMap.getStart();
                indexList = new ArrayList<>();
            }
            indexList.add(stationNetMap);
        }
        pathMap.add(indexList);
        return pathMap;
    }
}

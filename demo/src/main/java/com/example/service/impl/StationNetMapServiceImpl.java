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
import com.example.utils.Graph;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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
     *               返回每个顶点的边的集合 顶点顺序为W1..D1..C1..
     * @author pwz
     * @date 2022/9/20 11:31
     * @return List<List<StationNetMap>>
     */
    @Deprecated
    @Override
    public List<List<StationNetMap>> getMapData() {
        List<List<StationNetMap>> pathMap = new ArrayList<>();
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.orderByAsc("start");
        List<StationNetMap> stationNetMaps = stationNetMapMapper.selectList(queryWrapper1);
        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper2.groupBy("start");
        List<StationNetMap> nums = stationNetMapMapper.selectList(queryWrapper2);
        List<List<StationNetMap>> lists = new ArrayList<>();
        int start;
        for (int i = 0; i < nums.size(); i++) {
            start = nums.get(i).getStart();
            List<StationNetMap> list = new ArrayList<>();
            for (StationNetMap stationNetMap : stationNetMaps) {
                if (stationNetMap.getStart() == start || stationNetMap.getEndDid() == start) {
                    list.add(stationNetMap);
                }
            }
            lists.add(list);
        }
        QueryWrapper queryWrapper3 = new QueryWrapper();
        queryWrapper3.gt("end_cid", 0);
        queryWrapper3.orderByAsc("end_cid");
        List<StationNetMap> list = stationNetMapMapper.selectList(queryWrapper3);
        int end = list.get(0).getEndCid();
        List<StationNetMap> list1 = new ArrayList<>();
        for (StationNetMap stationNetMap : list) {

            if (end != stationNetMap.getEndCid()) {
                end = stationNetMap.getEndCid();
                lists.add(list1);
                list1 = new ArrayList<>();
            }
            list1.add(stationNetMap);
        }
        lists.add(list1);
        return lists;
    }

    /**
     * @Description: 生成邻接矩阵
     * @author pwz
     * @date 2022/9/23 9:49
     * @return int[][]
     */
    public int[][] getMatrix() {
        int droneCount = Math.toIntExact(droneStationMapper.selectCount(null));
        int carCount = Math.toIntExact(carStationMapper.selectCount(null));
        int[][] matrix = new int[droneCount + carCount][droneCount + carCount];
        for (int[] i : matrix) {
            Arrays.fill(i, Graph.maxDis);
        }
        List<StationNetMap> stationNetMaps = stationNetMapMapper.selectList(null);
        for (StationNetMap stationNetMap : stationNetMaps) {
            int x = stationNetMap.getStart() - 1;
            int y = stationNetMap.getEndDid() == 0
                    ? stationNetMap.getEndCid() - 1 + droneCount
                    : stationNetMap.getEndDid() - 1;
            matrix[x][y] = matrix[y][x] = stationNetMap.getDistance();
        }
        return matrix;
    }
}

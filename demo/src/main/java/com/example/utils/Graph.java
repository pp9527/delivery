package com.example.utils;


import com.example.bean.StationNetMap;
import com.example.service.CarStationService;
import com.example.service.DroneStationService;
import com.example.service.StationNetMapService;
import com.example.service.impl.StationNetMapServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/20 9:44
 * @Description:
 * @FileName: Graph
 */
@Component
public class Graph {
    private static List<String> stName; //图的顶点（站点）集合
    private static List<List<StationNetMap>> edges;// 每个顶点下的边的集合

//    获取非静态接口
    public static Graph graph;

    @PostConstruct
    public void init() {
        graph = this;

    }

    @Resource
    StationNetMapService netMapService;

    @Resource
    DroneStationService droneStationService;

    @Resource
    CarStationService carStationService;

    /**
     * @Description: 求图的顶点（站点）集合， 与边集合一一对应
     * @author pwz
     * @date 2022/9/22 19:39
     * @return java.lang.String[]
     */
    public static List<String> getStationNames() {
        List<String> droneStationNames = graph.droneStationService.getNames();
        List<String> carStationNames = graph.carStationService.getNames();
        stName.addAll(droneStationNames);
        stName.addAll(carStationNames);
        return stName;
    }

    /**
     * @Description: 地图的数据结构 返回每个顶点的边的集合 顶点顺序为W1..D1..C1..
     * @author pwz
     * @date 2022/9/22 19:18
     * @return List<List<StationNetMap>>
     */
    public static List<List<StationNetMap>> getMaps() {
        edges = graph.netMapService.getMapData();
        return edges;
    }


}
package com.example.utils;


import com.example.bean.StationNetMap;
import com.example.service.CarStationService;
import com.example.service.DroneStationService;
import com.example.service.StationNetMapService;
import lombok.Data;
import org.springframework.stereotype.Component;
import sun.security.provider.certpath.Vertex;

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

    public static final int maxDis = 10000; //最大距离
    private static List<String> vertex; //图的顶点（站点）集合
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
     * @Description: 求图的顶点（站点）集合，与边集合一一对应
     * @author pwz
     * @date 2022/9/22 19:39
     * @return java.lang.String[]
     */
    public static List<String> getVertex() {
        List<String> droneStationNames = graph.droneStationService.getNames();
        List<String> carStationNames = graph.carStationService.getNames();
        vertex = new ArrayList<>();
        vertex.addAll(droneStationNames);
        vertex.addAll(carStationNames);
        return vertex;
    }

    /**
     * @Description: 地图的数据结构 返回每个顶点的边的集合 顶点顺序为W1..D1..C1..
     * @author pwz
     * @date 2022/9/22 19:18
     * @return List<List<StationNetMap>>
     */
    @Deprecated
    public static List<List<StationNetMap>> getMaps() {
        edges = graph.netMapService.getMapData();
        return edges;
    }

    /**
     * @Description: 得到地图的邻接矩阵形式
     * @author pwz
     * @date 2022/9/23 10:31
     * @return int[][] ：顶点顺序W1..D1..C1..
     */
    public static int[][] getMatrix() {
        int[][] matrix = graph.netMapService.getMatrix();
        return matrix;
    }
}
package com.example.utils;


import com.example.bean.StationNetMap;
import com.example.service.CarStationService;
import com.example.service.CustomerService;
import com.example.service.DroneStationService;
import com.example.service.StationNetMapService;
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
public class GraphUtils {

    public static final int maxDis = 10000; //最大距离
    private static List<String> vertex; //图的顶点（站点）集合
    private static List<List<StationNetMap>> edges;// 每个顶点下的边的集合

//    获取非静态接口
    public static GraphUtils graphUtils;

    @PostConstruct
    public void init() {
        graphUtils = this;
    }

    @Resource
    StationNetMapService netMapService;

    @Resource
    DroneStationService droneStationService;

    @Resource
    CarStationService carStationService;

    @Resource
    CustomerService customerService;

    /**
     * @Description: 求图的顶点（站点）集合，与边集合一一对应 W1..D1..C1..
     * @author pwz
     * @date 2022/9/22 19:39
     * @return java.lang.String[]
     */
    public static List<String> getVertex() {
        List<String> droneStationNames = graphUtils.droneStationService.getNames();
        List<String> carStationNames = graphUtils.carStationService.getNames();
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
        edges = graphUtils.netMapService.getMapData();
        return edges;
    }

    /**
     * @Description: 得到地图的邻接矩阵形式
     * @author pwz
     * @date 2022/9/23 10:31
     * @return int[][] ：顶点顺序W1..D1..C1..
     */
    public static int[][] getMatrix() {
        int[][] matrix = graphUtils.netMapService.getMatrix();
        return matrix;
    }

    /**
     * @Description: 根据站点名返回顺序 W1:0、D1:1...
     * @author pwz
     * @date 2022/9/26 16:01
     * @param name
     * @return int
     */
    public static int getSequenceByName(String name) {
        return GraphUtils.getVertex().indexOf(name);
    }

    /**
     * @Description: 根据站点名集合返回站点坐标集合
     * @author pwz
     * @date 2022/10/21 15:00
     */
    public static List<List<Double>> stationNamesToLocations(List<String> stations) {
        List<List<Double>> list = new ArrayList<>();
        for (int i = 0; i < stations.size(); i++) {
            char[] chars = stations.get(i).toCharArray();
            List<Double> location;
            if (chars[0] == 'W' || chars[0] == 'D') {
                location = graphUtils.droneStationService.getLocationByName(stations.get(i));
            } else if (chars[0] == 'C'){
                location = graphUtils.carStationService.getLocationByName(stations.get(i));
            } else {
                location = graphUtils.customerService.getLocationByName(stations.get(i));
            }
            list.add(location);
        }
        return list;
    }
}
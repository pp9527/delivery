package com.example.utils;


import com.example.bean.StationNetMap;
import com.example.service.DroneStationService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author: pwz
 * @create: 2022/9/22 16:39
 * @Description:
 * @FileName: RoutePlanning
 */
@Component
public class RoutePlanning {
    private static int[] minDis;// 源点到其他各点的最短距离
    private static int[] pre;// 寻找最短路径时存放前驱节点下表的数组
    public static RoutePlanning routePlanning;

    @PostConstruct
    public void init() {
        routePlanning = this;
    }

    @Resource
    DroneStationService droneStationService;

    /**
     * @Description: 迪杰斯特拉算法求最短路径
     * @author pwz
     * @date 2022/9/22 20:11
     * @param sourceStationName
     * @param endStationName
     * @return List<List<Double>>
     */
    public static List<List<Double>> getShortestPath(String sourceStationName, String endStationName) {
//        无人机站点数量
        int droneCount = (int) routePlanning.droneStationService.count();
//        当前地图顶点和边的信息
        List<String> stationNames = Graph.getStationNames();
        List<List<StationNetMap>> edges = Graph.getMaps();
//        根据站点名找到源点和终点
        int source = stationNames.indexOf(sourceStationName);
        int end = stationNames.indexOf(endStationName);
//        初始化源点到其他点的距离为最大距离
        minDis = new int[stationNames.size()];
        Arrays.fill(minDis, Integer.MAX_VALUE);

        List<StationNetMap> list = edges.get(source);
        for (StationNetMap stationNetMap : list) {
            int endNum = stationNetMap.getEndDid() == 0
                    ? (int) (stationNetMap.getEndDid() + droneCount)
                    : stationNetMap.getEndDid();
            minDis[endNum] = stationNetMap.getDistance();
        }
        minDis[source] = 0;
        List<Integer> visitedVertex = new ArrayList<>();// 已确定最短路径的顶点
        visitedVertex.add(source);
        stationNames.remove(source);
        int curVertex = source, minDistance = Integer.MAX_VALUE;
        while (!visitedVertex.contains(end)) {
            for (StationNetMap stationNetMap : edges.get(curVertex)) {
                int startNum = stationNetMap.getStart();
                int endNum = stationNetMap.getEndDid() == 0
                        ? (int) (stationNetMap.getEndDid() + droneCount)
                        : stationNetMap.getEndDid();
                if (!visitedVertex.contains(startNum) && !visitedVertex.contains(endNum)) {
                    int min = stationNetMap.getDistance();
                    int num;
                    if (stationNetMap.getStart() == curVertex) {
                        num = stationNetMap.getEndDid() == 0
                                ? (int) (stationNetMap.getEndDid() + droneCount)
                                : stationNetMap.getEndDid();
                    } else if (stationNetMap.getEndDid() == curVertex) {
                        num = stationNetMap.getStart();
                    } else {
                        num = stationNetMap.getEndCid() + droneCount;
                    }
                    int minNum;
                    if (stationNetMap.getDistance() < min) {
                        minNum = num;
                    }
                }
                if (stationNetMap.getDistance() < minDistance) {
                    minDistance = stationNetMap.getDistance();
                }
            }
        }
        for (List<StationNetMap> edge : edges) {

        }
        return null;
    }
}
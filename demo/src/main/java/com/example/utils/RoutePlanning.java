package com.example.utils;


import com.example.bean.StationNetMap;
import com.example.service.CarStationService;
import com.example.service.CarToCustomerService;
import com.example.service.DroneStationService;
import net.sf.json.JSONArray;
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

    @Resource
    CarStationService carStationService;

    @Resource
    CarToCustomerService carToCustomerService;

    /**
     * @Description: 迪杰斯特拉算法求最短路径(方法未完成，太复杂，已舍弃)
     * @author pwz
     * @date 2022/9/22 20:11
     * @param sourceStationName
     * @param endStationName
     * @return List<List<Double>>
     */
    @Deprecated
    private static List<List<Double>> getShortestPath(String sourceStationName, String endStationName) {
//        无人机站点数量
        int droneCount = (int) routePlanning.droneStationService.count();
//        当前地图顶点和边的信息
        List<String> stationNames = Graph.getVertex();
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

    /**
     * @Description: 迪杰斯特拉算法求最短路径 邻接矩阵下标从0开始  0对应顶点W1
     * @author pwz
     * @date 2022/9/22 20:11
     * @param source
     * @param end
     * @return List<String> : 返回最短路径经过的站点，数组最后一位为最短距离，例：[W1, D7, C3, 2836]
     */
    private static List<String> getShortestPath(int source, int end) {
        int[][] matrix = Graph.getMatrix();  //地图的邻接矩阵
        List<String> vertex = Graph.getVertex();  //顶点数组W1-D1....C1...
        //最短路径长度
        int[] shortest = new int[matrix.length];
        //判断该点的最短路径是否求出
        int[] visited = new int[matrix.length];
        //存储输出路径
        String[] path = new String[matrix.length];

        //初始化输出路径
        for (int i = 0; i < matrix.length; i++) {
            path[i] = vertex.get(source) + ',' + vertex.get(i);
        }

        //初始化起点，将起点放入S
        shortest[source] = 0;
        visited[source] = 1;

        for (int i = 1; i < matrix.length; i++) {       //i从1开始，因为起点已经加入S了
            int min = Graph.maxDis;
            int index = -1;

            //找出某节点到起点路径最短
            for (int j = 0; j < matrix.length; j++) {
                //已经求出最短路径的节点不需要再加入计算并判断加入节点后是否存在更短路径
                if (visited[j] == 0 && matrix[source][j] < min) {
                    min = matrix[source][j];
                    index = j;
                }
            }

            //更新最短路径，标记起点到该节点的最短路径已经求出
            shortest[index] = min;
            visited[index] = 1;

            //更新从index跳到其它节点的较短路径
            for (int m = 0; m < matrix.length; m++) {
                if (visited[m] == 0 && matrix[source][index] + matrix[index][m] < matrix[source][m]) {
                    matrix[source][m] = matrix[source][index] + matrix[index][m];
                    path[m] = path[index] + ',' + vertex.get(m);
                }
            }
            if (visited[end] == 1) break;  // 找到目标节点跳出循环
        }

        String[] split = path[end].split(",");
        List<String> pathAndDistance = new ArrayList<>(Arrays.asList(split));
        pathAndDistance.add(String.valueOf(shortest[end]));
        return pathAndDistance;

        //打印最短路径
//        for (int i = 0; i < matrix.length; i++) {
//            if (i != source) {
//                if (shortest[i] == Graph.maxDis) {
//                    System.out.println(vertex.get(source) + "到" + vertex.get(i) + "不可达");
//                } else {
//                    System.out.println(vertex.get(source) + "到" + vertex.get(i) + "的最短路径为：" + path[i] + "，最短距离是：" + shortest[i]);
//                }
//            }
//        }
    }

    /**
     * @Description: 输入两站点名，返回最短路径经过的站点坐标集合
     * @author pwz
     * @date 2022/9/26 17:14
     * @param startStation : 出发站点
     * @param consignee : 到达站点
     * @return List<List<Double>>
     */
    public static List<List<Double>> getShortestPaths(String startStation, String consignee) {
        int source = Graph.getSequenceByName(startStation);
        int end = routePlanning.carToCustomerService.getShortestCarStationNum(consignee);
        List<String> stations = RoutePlanning.getShortestPath(source, end);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < stations.size() - 1; i++) {
            char[] chars = stations.get(i).toCharArray();
            List<Double> location;
            if (chars[0] == 'W' || chars[0] == 'D') {
                location = routePlanning.droneStationService.getLocationByName(stations.get(i));
            } else {
                location = routePlanning.carStationService.getLocationByName(stations.get(i));
            }
            jsonArray.add(location);
        }
        return jsonArray;
    }
}
package com.example.utils;


import com.example.bean.Car;
import com.example.bean.Drone;
import com.example.bean.StationNetMap;
import com.example.service.*;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: pwz
 * @create: 2022/9/22 16:39
 * @Description:
 * @FileName: RoutePlanning
 */
@Component
public class RoutePlanning {

    private static int[] minDis;// 源点到其他各点的最短距离
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

    @Resource
    DroneService droneService;

    @Resource
    CarService carService;

    /**
     * @param sourceStationName
     * @param endStationName
     * @return List<List < Double>>
     * @Description: 迪杰斯特拉算法求最短路径(方法未完成 、 数据结构未处理好 、 太复杂 、 已舍弃由getShortestPath ()代替 )
     * @author pwz
     * @date 2022/9/22 20:11
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
     * @param source
     * @param end
     * @return List<String> : 返回最短路径经过的站点，数组最后一位为最短距离，例：[W1, D7, C3, 2836]
     * @Description: 迪杰斯特拉算法求最短路径 邻接矩阵下标从0开始  0对应顶点W1
     * @author pwz
     * @date 2022/9/22 20:11
     */
    public static List<String> getShortestPath(int source, int end) {
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
     * @param startStation : 出发站点
     * @param consignee    : 到达站点
     * @return List<List < Double>>
     * 例：[[117.214161,31.75153],[117.213935,31.742092],[117.231156,31.735532]]
     * @Description: 优化目标：distance
     * 输入出发站点和目的地站点名，返回最短路径经过的站点坐标集合
     * @author pwz
     * @date 2022/9/26 17:14
     */
    public static List<List<Double>> getShortestDistanceRoute(String startStation, String consignee,
                                                              int uavType, int ugvType) {
        Drone drone = routePlanning.droneService.getById(uavType);
        Car car = routePlanning.carService.getById(ugvType);
        int source = Graph.getSequenceByName(startStation);
        int end = routePlanning.carToCustomerService.getShortestCarStationNum(consignee);
        List<String> path = RoutePlanning.getShortestPath(source, end);
        int[] timeAndEnergy = routePlanning.getTimeAndEnergy(path, consignee, drone, car);
        path.remove(path.size() - 1);
        return routePlanning.StationNameToRouteLocation(path, timeAndEnergy);
    }

    /**
     * @param model
     * @param startStation
     * @param consignee
     * @param objective
     * @return List<List < Double>>
     * @Description: 根据不同优化目标选择对应路径规划算法
     * @author pwz
     * @date 2022/10/13 16:46
     */
    public static List<List<Double>> selectStrategyByObjective(String model, String startStation
            , String consignee, String objective, int uavType, int ugvType) {
        List<List<Double>> Paths = null;
        switch (objective) {
            case "distance":
                // 地杰斯特拉最短路径
                Paths = getShortestDistanceRoute(startStation, consignee, uavType, ugvType);
                break;
            case "time":
                // 选择总时间最短的路线
                Paths = getShortestTimeRoute(model, startStation, consignee, uavType, ugvType);
                break;
            case "energy":
                // 选择总能耗最小的路线
                Paths = getShortestEnergyRoute(model, startStation, consignee, uavType, ugvType);
                break;
            case "energyInTime":
                // 选择时间约束下总能耗最小的路线
                break;
            default:
                break;
        }
        return Paths;
    }

    /**
     * @Description: 总时间最短的路线规划 优化目标：time
     * @author pwz
     * @date 2022/10/13 16:52
     */
    public static List<List<Double>> getShortestTimeRoute(String model, String startStation
            , String consignee, int uavType, int ugvType) {
        Drone drone = routePlanning.droneService.getById(uavType);
        Car car = routePlanning.carService.getById(ugvType);
        int source = Graph.getSequenceByName(startStation);
        // 遍历各个车站，计算总时间，选择最短时间的路径
        List<Integer> ends = routePlanning.carToCustomerService.getAllCarStationByCustomerName(consignee);
        for (int i = 0; i < ends.size(); i++) {
            // 获取车站在邻接矩阵中的顺序
            ends.set(i, (int) (ends.get(i) + routePlanning.droneStationService.count() - 1));
        }
        int totalTime = Integer.MAX_VALUE, totalEnergy = 0;
        // 保存时间最短路径和 总时间 总能耗
        List<String> res = null;
        for (int i = 0; i < ends.size(); i++) {
            List<String> path = RoutePlanning.getShortestPath(source, ends.get(i));
            int[] timeAndEnergy = routePlanning.getTimeAndEnergy(path, consignee, drone, car);
//            System.out.println(path.remove(path.size()) + "===" + dTime + " " + cTime + " " + (dTime + cTime));
            if (timeAndEnergy[0] < totalTime) {
                totalTime = timeAndEnergy[0];
                totalEnergy = timeAndEnergy[1];
                res = path;
            }
        }
        res.remove(res.size() - 1);
//        System.out.println(res);
        return routePlanning.StationNameToRouteLocation(res, new int[] {totalTime, totalEnergy});
    }

    /**
     * @return List<List < Double>>
     * @Description: 总能耗最小的路线规划
     * @author pwz
     * @date 2022/10/13 16:53
     */
    public static List<List<Double>> getShortestEnergyRoute(String model, String startStation
            , String consignee, int uavType, int ugvType) {
        Drone drone = routePlanning.droneService.getById(uavType);
        Car car = routePlanning.carService.getById(ugvType);
        int source = Graph.getSequenceByName(startStation);
        // 遍历各个车站，计算总能耗，选择能耗最小的路径
        List<Integer> ends = routePlanning.carToCustomerService.getAllCarStationByCustomerName(consignee);
        for (int i = 0; i < ends.size(); i++) {
            // 获取车站在邻接矩阵中的顺序
            ends.set(i, (int) (ends.get(i) + routePlanning.droneStationService.count() - 1));
        }
        int totalEnergy = Integer.MAX_VALUE, totalTime = 0;
        // 保存能耗最小路径 和 总时间 总能耗
        List<String> res = null;
        for (int i = 0; i < ends.size(); i++) {
            List<String> path = RoutePlanning.getShortestPath(source, ends.get(i));
            int[] timeAndEnergy = routePlanning.getTimeAndEnergy(path, consignee, drone, car);
            if (timeAndEnergy[1] < totalEnergy) {
                totalEnergy = timeAndEnergy[1];
                totalTime = timeAndEnergy[0];
                res = path;
            }
        }
        res.remove(res.size() - 1);
//        System.out.println(res);
        return routePlanning.StationNameToRouteLocation(res, new int[] {totalTime, totalEnergy});
    }

    /**
     * @return List<List < Double>>
     * @Description: 时间约束下总能耗最小的路线规划
     * @author pwz
     * @date 2022/10/13 16:53
     */
    public static List<List<Double>> getShortestEnergyRouteUnderTimeConstraint() {
        return null;
    }

    /**
     * @param startStation
     * @param consignee
     * @return List<String>
     * 例：[W1, D7, C3]
     * @Description: 返回最短路径经过的站点集合
     * @author pwz
     * @date 2022/9/27 14:44
     */
    public static List<String> getShortestStationName(String startStation, String consignee) {
        int source = Graph.getSequenceByName(startStation);
        int end = routePlanning.carToCustomerService.getShortestCarStationNum(consignee);
        List<String> stations = RoutePlanning.getShortestPath(source, end);
        stations.remove(stations.size() - 1);
        return stations;
    }

    /**
     * @description: 统一把路径规划的结果站点名字集合转换成坐标集合
     * @author: pwz
     * @date: 2022/10/13 22:47
     * @param: [stations]
     * @return: java.util.List<java.lang.Double>
     **/
    private List<List<Double>> StationNameToRouteLocation(List<String> stations, int[] timeAndEnergy) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < stations.size(); i++) {
            char[] chars = stations.get(i).toCharArray();
            List<Double> location;
            if (chars[0] == 'W' || chars[0] == 'D') {
                location = routePlanning.droneStationService.getLocationByName(stations.get(i));
            } else {
                location = routePlanning.carStationService.getLocationByName(stations.get(i));
            }
            jsonArray.add(location);
        }
        jsonArray.add(timeAndEnergy);
        return jsonArray;
    }

    /**
     * @Description: 输入路径 求总时间和总能耗
     * @author pwz
     * @date 2022/10/14 12:08
     * @param path
     * @param consignee
     * @param drone
     * @param car
     * @return int[]
     */
    private static int[] getTimeAndEnergy(List<String> path, String consignee, Drone drone, Car car) {
        // 无人机飞行总距离 preDistance
        int preDistance = Integer.parseInt(path.get(path.size() - 1));
        String driveSource = path.get(path.size() - 2);
        // 无人机路程总时间 dTime
        int dTime = preDistance / drone.getSpeed();
        int carRouteDistance = GuideRoutePlanUtils.getDistanceOfPlanFromGuide(driveSource, consignee);
        // 无人车路程总时间 cTime
        int cTime = carRouteDistance / car.getSpeed();
        // 无人机路程总能耗 dEnergy
        int dEnergy = dTime * drone.getNoLoadPower();
        // 无人车路程总能耗 dEnergy
        int cEnergy = cTime * car.getNoLoadPower();
        // 总时间
        int totalTime = dTime + cTime;
        // 总能耗
        int totalEnergy = cEnergy + dEnergy;
        return new int[] {totalTime / 60, totalEnergy/ 1000};
    }
}
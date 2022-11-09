package com.example.utils;


import com.example.bean.Car;
import com.example.bean.Drone;
import com.example.bean.Order;
import com.example.service.*;
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
    public static RoutePlanning routePlanning;

//    private static GuideRoutePlanUtils guideRoutePlanUtils;

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
    }

//    /**
//     * @return List<List<Double>>
//     * @Description: 根据不同优化目标选择对应路径规划算法  返回前台所需坐标集合
//     * @author pwz
//     * @date 2022/10/13 16:46
//     */
//    public static List<List<Double>> selectStrategyByObjective(
//            Order order, String objective, int uavType, int ugvType) {
//        int f = 0;
//        List<List<Double>> res;
//        // 最短路径途径站点和距离  W1, D1, C1, 2455
//        List<String> route = null;
//        // 路径的时间和能耗
//        int[] timeAndEnergy;
//        int weigh = (int) (order.getWeight() * 1000);
//        Drone drone = routePlanning.droneService.getById(uavType);
//        Car car = routePlanning.carService.getById(ugvType);
//        // 负载是否能承受判断
//        int flag = loadJudge(drone, car, weigh);
//        if (flag != 1) {
//            // 不能承受，返回提示标志位
//            res = routePlanning.stationNameToRouteLocation(null, new int[]{flag, flag});
//        } else {
//            // 能承受，继续
//            switch (objective) {
//                case "distance":
//                    // 地杰斯特拉最短路径
//                    System.out.println("已选择距离用户最近的无人车站点作为中继节点...");
//                    route = getShortestDistanceRoute(order.getStartStation(), order.getConsignee());
//                    break;
//                case "time":
//                    // 选择总时间最短的路线
//                    System.out.println("当前选择总时间最短方案...");
//
//                    //使用openfaas函数
////                    String carToUserDistance = GuideRoutePlanUtils.getCarToUserDistance(
////                            routePlanning.carToCustomerService.getAllCarStationNameByCustomerName(
////                                    order.getConsignee()), order.getConsignee());
////                    f = 1;
////                    String path = OpenFaasUtils.getShortestTimePath(order.getStartStation(), order.getConsignee()
////                            , uavType - 1, ugvType - 1, weigh, carToUserDistance);
////                    String[] split = path.split(",");
////                    route = Arrays.asList(split);
//
//                    // 不使用使用openfaas函数
//                    route = getShortestTimeRoute(order.getStartStation(), order.getConsignee(), drone, car, weigh);
//                    break;
//                case "energy":
//                    // 选择总能耗最小的路线
//                    System.out.println("当前选择总能耗最小方案...");
//                    route = getShortestEnergyRoute(order.getStartStation(), order.getConsignee(), drone, car, weigh);
//                    break;
//                case "energyInTime":
//                    // 选择时间约束下总能耗最小的路线
//                    System.out.println("当前选择时间约束下总能耗最小方案...");
//                    route = getShortestEnergyRouteUnderTimeConstraint(order.getStartStation(),
//                            order.getConsignee(), drone, car, order.getDeadline() * 60, weigh);
//                    if (route == null) return null;
//                    break;
//            }
//            if (f == 1) {
//                timeAndEnergy = new int[]{Integer.parseInt(route.get(route.size() - 2)),
//                        Integer.parseInt(route.get(route.size() - 1))};
//                route.remove(route.size() - 1);
//            } else {
//                timeAndEnergy = getTimeAndEnergy(route, order.getConsignee(), drone, car, weigh);
//            }
//            route.remove(route.size() - 1);
//            System.out.println("符合要求方案为：" + route + ", 总时间：" +
//                    timeAndEnergy[0] + "s, 总能耗：" + timeAndEnergy[1] + "j");
//            res = routePlanning.stationNameToRouteLocation(route, timeAndEnergy);
//        }
//        return res;
//    }


    /**
     * @return List<String>
     * @Description: 根据不同优化目标选择对应路径规划算法  返回返回路径途经站点集合
     * @author pwz
     * @date 2022/10/20 15:17
     */
    public static List<String> getRouteByObjective(Order order, String objective, int uavType, int ugvType) {
        Drone drone = routePlanning.droneService.getById(uavType);
        Car car = routePlanning.carService.getById(ugvType);
        int weigh = (int) (order.getWeight() * 1000);
        List<String> route = null;
        switch (objective) {
            case "distance":
                // 地杰斯特拉最短路径
                route = getShortestDistanceRoute(order.getStartStation(), order.getConsignee());
                route.remove(route.size() - 1);
                break;
            case "time":
                // 选择总时间最短的路线
                route = getShortestTimeRoute(order.getStartStation(), order.getConsignee(), drone, car, weigh);
                route.remove(route.size() - 1);
                break;
            case "energy":
                // 选择总能耗最小的路线
                route = getShortestEnergyRoute(order.getStartStation(), order.getConsignee(), drone, car, weigh);
                route.remove(route.size() - 1);
                break;
            case "energyInTime":
                // 选择时间约束下总能耗最小的路线
                route = getShortestEnergyRouteUnderTimeConstraint(order.getStartStation(),
                        order.getConsignee(), drone, car, order.getDeadline() * 60, weigh);
                route.remove(route.size() - 1);
                break;
            default:
                break;
        }
        return route;
    }

    /**
     * @return List<String>
     * @Description: 优化目标：distance
     * 输入出发站点和目的地站点名，返回最短路径经过的站点坐标集合
     * @author pwz
     * @date 2022/9/26 17:14
     */
    public static List<String> getShortestDistanceRoute(String startStation, String consignee) {
        int source = Graph.getSequenceByName(startStation);
        int end = routePlanning.carToCustomerService.getShortestCarStationNum(consignee);
        List<String> path = RoutePlanning.getShortestPath(source, end);
        return path;
    }

    /**
     * @return
     * @Description: 总时间最短的路线规划 优化目标：time
     * @author pwz
     * @date 2022/10/13 16:52
     */
    public static List<String> getShortestTimeRoute(String startStation
            , String consignee, Drone drone, Car car, int weigh) {
        int source = Graph.getSequenceByName(startStation);
        // 遍历各个车站，计算总时间，选择最短时间的路径
        List<Integer> ends = routePlanning.carToCustomerService.getAllCarStationByCustomerName(consignee);
        for (int i = 0; i < ends.size(); i++) {
            // 获取车站在邻接矩阵中的顺序
            ends.set(i, (int) (ends.get(i) + routePlanning.droneStationService.count() - 1));
        }
        int totalTime = Integer.MAX_VALUE;
        // 保存时间最短路径和 总时间 总能耗
        List<String> res = new ArrayList<>();
        for (int i = 0; i < ends.size(); i++) {
            List<String> path = RoutePlanning.getShortestPath(source, ends.get(i));
            int[] timeAndEnergy = routePlanning.getTimeAndEnergy(path, consignee, drone, car, weigh);
            if (timeAndEnergy[0] < totalTime) {
                totalTime = timeAndEnergy[0];
                res.clear();
                res.addAll(path);
            }
            path.remove(path.size() - 1);
            System.out.println("方案" + i + ":" + path + ", 时间：" + timeAndEnergy[0] +
                    "s, 能耗：" + timeAndEnergy[1] + "j");
        }
        return res;
//        return routePlanning.stationNameToRouteLocation(res, new int[]{totalTime, totalEnergy});
    }

    /**
     * @return List<List < Double>>
     * @Description: 总能耗最小的路线规划
     * @author pwz
     * @date 2022/10/13 16:53
     */
    public static List<String> getShortestEnergyRoute(String startStation
            , String consignee, Drone drone, Car car, int weigh) {
        int source = Graph.getSequenceByName(startStation);
        // 遍历各个车站，计算总能耗，选择能耗最小的路径
        List<Integer> ends = routePlanning.carToCustomerService.getAllCarStationByCustomerName(consignee);
        for (int i = 0; i < ends.size(); i++) {
            // 获取车站在邻接矩阵中的顺序
            ends.set(i, (int) (ends.get(i) + routePlanning.droneStationService.count() - 1));
        }
        int totalEnergy = Integer.MAX_VALUE;
        // 保存能耗最小路径 和 总时间 总能耗
        List<String> res = new ArrayList<>();
        for (int i = 0; i < ends.size(); i++) {
            List<String> path = RoutePlanning.getShortestPath(source, ends.get(i));
            int[] timeAndEnergy = routePlanning.getTimeAndEnergy(path, consignee, drone, car, weigh);
            if (timeAndEnergy[1] < totalEnergy) {
                totalEnergy = timeAndEnergy[1];
                res.clear();
                res.addAll(path);
            }
            path.remove(path.size() - 1);
            System.out.println("方案" + i + ":" + path + ", 时间：" + timeAndEnergy[0] +
                    "s, 能耗：" + timeAndEnergy[1] + "j");
        }
        return res;
    }

    /**
     * @return List<List < Double>>
     * @Description: 时间约束下总能耗最小的路线规划
     * @author pwz
     * @date 2022/10/13 16:53
     */
    public static List<String> getShortestEnergyRouteUnderTimeConstraint(String startStation
            , String consignee, Drone drone, Car car, int time, int weigh) {
        int source = Graph.getSequenceByName(startStation);
        // 遍历各个车站，计算总能耗，选择能耗最小的路径
        List<Integer> ends = routePlanning.carToCustomerService.getAllCarStationByCustomerName(consignee);
        for (int i = 0; i < ends.size(); i++) {
            // 获取车站在邻接矩阵中的顺序
            ends.set(i, (int) (ends.get(i) + routePlanning.droneStationService.count() - 1));
        }
        int totalEnergy = Integer.MAX_VALUE;
        // 保存能耗最小路径 和 总时间 总能耗
        List<String> res = new ArrayList<>();
        for (int i = 0; i < ends.size(); i++) {
            List<String> path = RoutePlanning.getShortestPath(source, ends.get(i));
            int[] timeAndEnergy = routePlanning.getTimeAndEnergy(path, consignee, drone, car, weigh);
            // 时间约束下能耗最低
            if (timeAndEnergy[1] < totalEnergy && timeAndEnergy[0] < time) {
                totalEnergy = timeAndEnergy[1];
                res.clear();
                res.addAll(path);
            }
            path.remove(path.size() - 1);
            System.out.println("方案" + i + ":" + path + ", 时间：" + timeAndEnergy[0] +
                    "s, 能耗：" + timeAndEnergy[1] + "j");
        }
        if (res.size() == 0) {
            System.out.println("该条件下没有可行的服务方案，请修改订单信息！");
            return null;
        }
        return res;
    }

    /**
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
     * @return: java.util.List<java.lang.Double>
     **/
    public List<List<Double>> stationNameToRouteLocation(List<String> stations, int[] timeAndEnergy) {
        JSONArray jsonArray = new JSONArray();
        if (stations == null) {
            jsonArray.add(timeAndEnergy);
            return jsonArray;
        }
        jsonArray.add(stations);
        List<List<Double>> list = new ArrayList<>();
        for (int i = 0; i < stations.size(); i++) {
            char[] chars = stations.get(i).toCharArray();
            List<Double> location;
            if (chars[0] == 'W' || chars[0] == 'D') {
                location = routePlanning.droneStationService.getLocationByName(stations.get(i));
            } else {
                location = routePlanning.carStationService.getLocationByName(stations.get(i));
            }
            list.add(location);
        }
        jsonArray.add(list);
        timeAndEnergy[0] /= 60;
        timeAndEnergy[1] /= 1000;
        jsonArray.add(timeAndEnergy);
        return jsonArray;
    }

    /**
     * @return int[]
     * @Description: 输入路径 求总时间和总能耗
     * @author pwz
     * @date 2022/10/14 12:08
     */
    public static int[] getTimeAndEnergy(List<String> path, String consignee, Drone drone, Car car, int weigh) {
        // 无人机飞行总距离 preDistance
        int preDistance = Integer.parseInt(path.get(path.size() - 1));
        String driveSource = path.get(path.size() - 2);
        // 无人机路程总时间 dTime
        int dTime = preDistance / drone.getSpeed();
        int carRouteDistance = GuideRoutePlanUtils.getDistanceOfPlanFromGuide(driveSource, consignee);
        // 无人车路程总时间 cTime
        int cTime = carRouteDistance / car.getSpeed();
        // 无人机实时功耗
        int realDronePower = getDronePower(drone, weigh);
        // 无人机路程总能耗 dEnergy
        int dEnergy = dTime * realDronePower;
        // 无人车实时功耗
        int realCarPower = getCarPower(car, weigh);
        // 无人车路程总能耗 dEnergy
        int cEnergy = cTime * realCarPower;
        // 总时间
        int totalTime = dTime + cTime;
        // 总能耗
        int totalEnergy = cEnergy + dEnergy;
        return new int[]{totalTime, totalEnergy};
    }

    // 无人机实时功耗
    private static int getDronePower(Drone drone, int weight) {
        return drone.getNoLoadPower() // 功耗线性变化   空载功耗 + 载货增量
                + (drone.getMaxPower() - drone.getNoLoadPower()) * weight / drone.getMaxLoad();
    }

    // 无人车实时功耗
    private static int getCarPower(Car car, int weight) {
        return car.getNoLoadPower() // 功耗线性变化   空载功耗 + 载货增量
                + (car.getMaxPower() - car.getNoLoadPower()) * weight / car.getMaxLoad();
    }

    /**
     * @return int
     * @Description: 负载判断、提示信息
     * @author pwz
     * @date 2022/10/17 15:43
     */
    public static int loadJudge(Drone drone, Car car, int weigh) {
        // 无人机无人车负载是否能承受
        if (weigh > drone.getMaxLoad()) {
            System.out.println("当前无人机无法承受当前负载！请选择更大无人机！");
            return -1;
        } else if (weigh > car.getMaxLoad()) {
            System.out.println("当前无人车无法承受当前负载！请选择更大无人车！");
            return -2;
        } else {
            return 1;
        }
    }

    /**
     * @return String
     * @Description: 将路径规划的结果转为字符串，返回可以存储的字符串
     * @author pwz
     * @date 2022/10/20 15:43
     */
    public static String pathListToString(List<String> route, String destination) {
        String res = "";
        for (String s : route) {
            res = res + s + "->";
        }
        res += destination;
        return res;
    }

}
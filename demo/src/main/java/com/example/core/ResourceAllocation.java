package com.example.core;

import com.example.bean.Car;
import com.example.bean.Drone;
import com.example.bean.Order;
import com.example.service.CarService;
import com.example.service.CarToCustomerService;
import com.example.service.DroneService;
import com.example.utils.GraphUtils;
import com.example.utils.GuideRoutePlanUtils;
import com.example.utils.OpenFaasUtils;
import com.example.utils.RoutePlanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/11/9 15:53
 * @Description: 资源分配
 * @FileName: ResourceAllocation
 */
@Component
public class ResourceAllocation {

    @Resource
    RoutePlanUtils routePlanUtils;

    @Resource
    DroneService droneService;

    @Resource
    CarService carService;

    @Resource
    CarToCustomerService carToCustomerService;

    /**
     * @return List<List<Double>>
     * @Description: 资源分配   根据不同优化目标选择对应路径规划算法  返回前台所需坐标集合
     * @author pwz
     * @date 2022/10/13 16:46
     */
    public List<List<Double>> selectStrategyByObjective(
            Order order, String objective, int uavType, int ugvType) {
        // 时间能耗标志位f  1：时间能耗已经求出 0：未求出
        int f = 0;
        // 运行环境标志位 0：请求本地服务  1：请求openfaas服务
        int environmentFlag = 0;
        List<List<Double>> res;
        // 最短路径途径站点和距离  W1, D1, C1, 2455
        List<String> route = null;
        String path;
        // 路径的时间和能耗
        int[] timeAndEnergy;
        int weigh = (int) (order.getWeight() * 1000);
        Drone drone = droneService.getById(uavType);
        Car car = carService.getById(ugvType);
        // 负载是否能承受判断
        int flag = routePlanUtils.loadJudge(drone, car, weigh);
        if (flag != 1) {
            // 货物重量不能承受，返回提示标志位
            res = routePlanUtils.stationNameToRouteLocation(null, new int[]{flag, flag});
        } else {
            switch (objective) {
                case "distance":     // 地杰斯特拉最短路径
                    if (environmentFlag == 0) {
                        // 使用本地服务
                    route = routePlanUtils.getShortestDistanceRoute(order.getStartStation(), order.getConsignee());
                    } else {
                        //使用openfaas服务
                        int source = GraphUtils.getSequenceByName(order.getStartStation());
                        int end = carToCustomerService.getShortestCarStationNum(order.getConsignee());
                        path = OpenFaasUtils.getShortestDistancePath2(source, end);
                        String[] split1 = path.split("->|,");
                        route = new ArrayList<>(Arrays.asList(split1));
                    }
                    break;
                case "time":       // 选择总时间最短的路线
                    if (environmentFlag == 0) {
                        // 使用本地服务
                    route = routePlanUtils.getShortestTimeRoute(order.getStartStation(), order.getConsignee(),
                            drone, car, weigh);
                    } else {
                        //使用openfaas服务
                        String carToUserDistance1 = GuideRoutePlanUtils.getCarToUserDistance(
                                carToCustomerService.getAllCarStationNameByCustomerName(
                                        order.getConsignee()), order.getConsignee());
                        f = 1;
                        path = OpenFaasUtils.getShortestTimePath2(order.getStartStation(), order.getConsignee()
                                , uavType - 1, ugvType - 1, weigh, carToUserDistance1);
                        String[] split2 = path.split(",");
                        route = new ArrayList<>(Arrays.asList(split2));
                    }
                    break;
                case "energy":       // 选择总能耗最小的路线
                    if (environmentFlag == 0) {
                        // 使用本地服务
                    route = routePlanUtils.getShortestEnergyRoute(order.getStartStation(), order.getConsignee(),
                            drone, car, weigh);
                    } else {
                        //使用openfaas服务
                        String carToUserDistance2 = GuideRoutePlanUtils.getCarToUserDistance(
                                carToCustomerService.getAllCarStationNameByCustomerName(
                                        order.getConsignee()), order.getConsignee());
                        f = 1;
                        path = OpenFaasUtils.getShortestEnergyPath2(order.getStartStation(), order.getConsignee()
                                , uavType - 1, ugvType - 1, weigh, carToUserDistance2);
                        String[] split3 = path.split(",");
                        route = new ArrayList<>(Arrays.asList(split3));
                    }
                    break;
                case "energyInTime":      // 选择时间约束下总能耗最小的路线
                    if (environmentFlag == 0) {
                        // 使用本地服务
                    route = routePlanUtils.getShortestEnergyRouteUnderTimeConstraint(order.getStartStation(),
                            order.getConsignee(), drone, car, order.getDeadline() * 60, weigh);
                    } else {
                        //使用openfaas服务
                        String carToUserDistance3 = GuideRoutePlanUtils.getCarToUserDistance(
                                carToCustomerService.getAllCarStationNameByCustomerName(
                                        order.getConsignee()), order.getConsignee());
                        f = 1;
                        path = OpenFaasUtils.getShortestEnergyInTimePath2(order.getStartStation(), order.getConsignee(),
                                uavType - 1, ugvType - 1, order.getDeadline(), weigh, carToUserDistance3);
                        String[] split4 = path.split(",");
                        route = new ArrayList<>(Arrays.asList(split4));
                    }
                    if (route == null) return null;
                    break;
            }
            if (f == 1) {
                timeAndEnergy = new int[]{Integer.parseInt(route.get(route.size() - 2)),
                        Integer.parseInt(route.get(route.size() - 1))};
                route.remove(route.size() - 1);
            } else {
                timeAndEnergy = routePlanUtils.getTimeAndEnergy(route, order.getConsignee(), drone, car, weigh);
            }
            route.remove(route.size() - 1);
            System.out.println("The final scheme is：" + route + ", total time：" +
                    timeAndEnergy[0] + "s, total energy：" + timeAndEnergy[1] + "j");
            res = routePlanUtils.stationNameToRouteLocation(route, timeAndEnergy);
        }
        return res;
    }
}
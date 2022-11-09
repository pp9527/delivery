package com.example.utils;

import com.example.bean.Car;
import com.example.bean.Drone;
import com.example.bean.Order;
import com.example.service.CarService;
import com.example.service.DroneService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/11/9 15:54
 * @Description: 服务组合
 * @FileName: ServiceComposition
 */
@Component
public class ServiceComposition {

    @Resource
    RoutePlanning routePlanning;

    @Resource
    DroneService droneService;

    @Resource
    CarService carService;

    /**
     * @return List<List<Double>>
     * @Description: 服务组合   根据不同优化目标选择对应路径规划算法  返回前台所需坐标集合
     * @author pwz
     * @date 2022/10/13 16:46
     */
    public List<List<Double>> selectStrategyByObjective(
            Order order, String objective, int uavType, int ugvType) {
        int f = 0;
        List<List<Double>> res;
        // 最短路径途径站点和距离  W1, D1, C1, 2455
        List<String> route = null;
        // 路径的时间和能耗
        int[] timeAndEnergy;
        int weigh = (int) (order.getWeight() * 1000);
        Drone drone = droneService.getById(uavType);
        Car car = carService.getById(ugvType);
        // 负载是否能承受判断
        int flag = routePlanning.loadJudge(drone, car, weigh);
        if (flag != 1) {
            // 不能承受，返回提示标志位
            res = routePlanning.stationNameToRouteLocation(null, new int[]{flag, flag});
        } else {
            // 能承受，继续
            switch (objective) {
                case "distance":
                    // 地杰斯特拉最短路径
                    System.out.println("已选择距离用户最近的无人车站点作为中继节点...");
                    route = routePlanning.getShortestDistanceRoute(order.getStartStation(), order.getConsignee());
                    break;
                case "time":
                    // 选择总时间最短的路线
                    System.out.println("当前选择总时间最短方案...");

                    //使用openfaas函数
//                    String carToUserDistance = GuideRoutePlanUtils.getCarToUserDistance(
//                            routePlanning.carToCustomerService.getAllCarStationNameByCustomerName(
//                                    order.getConsignee()), order.getConsignee());
//                    f = 1;
//                    String path = OpenFaasUtils.getShortestTimePath(order.getStartStation(), order.getConsignee()
//                            , uavType - 1, ugvType - 1, weigh, carToUserDistance);
//                    String[] split = path.split(",");
//                    route = Arrays.asList(split);

                    // 不使用使用openfaas函数
                    route = routePlanning.getShortestTimeRoute(order.getStartStation(), order.getConsignee(), drone, car, weigh);
                    break;
                case "energy":
                    // 选择总能耗最小的路线
                    System.out.println("当前选择总能耗最小方案...");
                    route = routePlanning.getShortestEnergyRoute(order.getStartStation(), order.getConsignee(), drone, car, weigh);
                    break;
                case "energyInTime":
                    // 选择时间约束下总能耗最小的路线
                    System.out.println("当前选择时间约束下总能耗最小方案...");
                    route = routePlanning.getShortestEnergyRouteUnderTimeConstraint(order.getStartStation(),
                            order.getConsignee(), drone, car, order.getDeadline() * 60, weigh);
                    if (route == null) return null;
                    break;
            }
            if (f == 1) {
                timeAndEnergy = new int[]{Integer.parseInt(route.get(route.size() - 2)),
                        Integer.parseInt(route.get(route.size() - 1))};
                route.remove(route.size() - 1);
            } else {
                timeAndEnergy = routePlanning.getTimeAndEnergy(route, order.getConsignee(), drone, car, weigh);
            }
            route.remove(route.size() - 1);
            System.out.println("符合要求方案为：" + route + ", 总时间：" +
                    timeAndEnergy[0] + "s, 总能耗：" + timeAndEnergy[1] + "j");
            res = routePlanning.stationNameToRouteLocation(route, timeAndEnergy);
        }
        return res;
    }
}
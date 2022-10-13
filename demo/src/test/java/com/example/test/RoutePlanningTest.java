package com.example.test;

/**
 * @author: pwz
 * @create: 2022/9/26 16:20
 * @Description:
 * @FileName: testRoutePlanning
 */

import com.example.service.CarToCustomerService;
import com.example.utils.Graph;
import com.example.utils.RoutePlanning;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RoutePlanningTest {

    @Resource
    CarToCustomerService carToCustomerService;

    @Test
    public void testGetShortestPath() {
        int source = Graph.getSequenceByName("W1");
        int end = carToCustomerService.getShortestCarStationNum("U1");
//        List<String> paths = RoutePlanning.getShortestPath(source, end);
//        System.out.println(paths);
    }

    @Test
    public void testGetShortestPaths() {
        System.out.println(RoutePlanning.getShortestDistanceRoute("W1", "U1"));
    }

    @Test
    public void testGetShortestStationName() {
        System.out.println(RoutePlanning.getShortestStationName("W1", "U1"));
    }
}
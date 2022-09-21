package com.example;

import com.example.bean.Order;
import com.example.bean.StationNetMap;
import com.example.bean.Warehouse;
import com.example.mapper.OrderMapper;
import com.example.mapper.WarehouseMapper;
import com.example.service.StationNetMapService;
import com.example.utils.Graph;
import com.example.utils.PageUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class DemoApplicationTests {

    @Resource
    OrderMapper orderMapper;

    @Resource
    WarehouseMapper warehouseMapper;

    @Resource
    StationNetMapService stationNetMapService;

    @Test
    void testOrderMapper() {

//        orderMapper.selectList(null).forEach(System.out::println);
//        for (int idNum = 11; idNum < 100; idNum++) {
//            orderMapper.insert(new Order(idNum, 1, 12, 122, idNum, "张三", 10, 10, 10, 1.2, "西瓜", new Date(System.currentTimeMillis())));
//        }
//        Order order = orderMapper.selectById(1);
//        System.out.println(order.getId);

    }

    @Test
    public void testPageUtils() {
        System.out.println(PageUtils.pageNav(3, 10, 5).toString());
    }

    @Test
    public void testWarehouseMapper() {
        List<Warehouse> warehouses = warehouseMapper.selectList(null);
        for (Warehouse warehouse : warehouses) {
            System.out.println(warehouse.getLongitude());
        }
    }

    @Test
    public void testMap() {
        System.out.println(stationNetMapService.getMapData());
//        System.out.println(stationNetMapService.getAllStationPath());
    }

    @Test
    public void testGraphGetMaps() {
        List<List<StationNetMap>> maps = Graph.getMaps();
        System.out.println(maps);
    }


}

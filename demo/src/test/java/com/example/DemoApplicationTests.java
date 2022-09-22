package com.example;

import com.example.bean.Customer;
import com.example.bean.StationNetMap;
import com.example.bean.Warehouse;
import com.example.mapper.OrderMapper;
import com.example.mapper.WarehouseMapper;
import com.example.service.CarToCustomerService;
import com.example.service.CustomerService;
import com.example.service.StationNetMapService;
import com.example.utils.Graph;
import com.example.utils.PageUtils;
import net.sf.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@SpringBootTest
class DemoApplicationTests {

    @Resource
    OrderMapper orderMapper;

    @Resource
    WarehouseMapper warehouseMapper;

    @Resource
    StationNetMapService stationNetMapService;

    @Resource
    CustomerService customerService;

    @Resource
    CarToCustomerService carToCustomerService;

    @Test
    public void testGetAllCarToCustomerPath() {
        List<List<List<Double>>> allCarToCustomerPath = carToCustomerService.getAllCarToCustomerPath();
        System.out.println(allCarToCustomerPath);
    }


}

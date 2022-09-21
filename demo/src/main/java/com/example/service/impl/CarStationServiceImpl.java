package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bean.CarStation;
import com.example.mapper.CarStationMapper;
import com.example.service.CarStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/15 18:57
 * @Description:
 * @FileName: CarStationServiceImpl
 */
@Service
public class CarStationServiceImpl extends ServiceImpl<CarStationMapper, CarStation>
        implements CarStationService {

    @Resource
    CarStationMapper carStationMapper;

    @Override
    public CarStation selectById(int id) {
        CarStation carStation = carStationMapper.selectById(id);
        return carStation;
    }

    @Override
    public List<String> getNames() {
        List<CarStation> carStations = carStationMapper.selectList(null);
        List<String> stationNames = new ArrayList<>();
        for (CarStation carStation : carStations) {
            stationNames.add(carStation.getName());
        }
        return stationNames;
    }

    @Override
    public List<List<Double>> getLongitudesAndLatitudes() {
        List<CarStation> carStations = carStationMapper.selectList(null);
        List<List<Double>> longitudesAndLatitudes = new ArrayList<>();
        for (CarStation carStation : carStations) {
            ArrayList<Double> list = new ArrayList<>();
            list.add(carStation.getLongitude());
            list.add(carStation.getLatitude());
            longitudesAndLatitudes.add(list);
        }
        return longitudesAndLatitudes;
    }
}
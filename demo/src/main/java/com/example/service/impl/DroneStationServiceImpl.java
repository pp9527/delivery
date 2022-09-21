package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bean.DroneStation;
import com.example.mapper.DroneStationMapper;
import com.example.service.DroneStationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/15 18:58
 * @Description:
 * @FileName: DroneStationServiceImpl
 */
@Service
public class DroneStationServiceImpl extends ServiceImpl<DroneStationMapper, DroneStation>
        implements DroneStationService {

    @Resource
    DroneStationMapper droneStationMapper;

    @Override
    public List<String> getNames() {
        List<DroneStation> droneStations = droneStationMapper.selectList(null);
        List<String> stationNames = new ArrayList<>();
        for (DroneStation droneStation : droneStations) {
            stationNames.add(droneStation.getName());
        }
        return stationNames;
    }

    @Override
    public List<List<Double>> getLongitudesAndLatitudes() {
        List<DroneStation> droneStations = droneStationMapper.selectList(null);
        List<List<Double>> longitudesAndLatitudes = new ArrayList<>();
        for (DroneStation droneStation : droneStations) {
            ArrayList<Double> list = new ArrayList<>();
            list.add(droneStation.getLongitude());
            list.add(droneStation.getLatitude());
            longitudesAndLatitudes.add(list);
        }
        return longitudesAndLatitudes;
    }
}
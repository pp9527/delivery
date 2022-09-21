package com.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: pwz
 * @create: 2022/9/15 18:41
 * @Description:
 * @FileName: DroneStation
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DroneStation {

    private Integer id;
    private String name;
    private double longitude;
    private double latitude;
}
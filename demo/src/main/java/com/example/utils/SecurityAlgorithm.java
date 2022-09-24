package com.example.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author: pwz
 * @create: 2022/9/23 11:16
 * @Description:
 * @FileName: SecurityAlgorithm
 */
@Component
public class SecurityAlgorithm {

    public static void main(String[] args) {
        double[] location = new double[2];

        location[0] = 117.220599;
        location[1] = 31.730276;
        geoDp(location);

    }

    /**
     *
     * @paper： Geo-Indistinguishability: Differential Privacy for Location-Based Systems
     * @Author ZZ
     * @param location 传入的原始位置坐标
     * @return location_dp 返回加入DP噪声扰动后的坐标
     */
    public static double[] geoDp(double[] location){
        double theta ;
        double p;
        double radius;
        double eps = 10.0;       // 隐私预算的大小，可更改；小eps大扰动。
        double[] location_dp = new double[2];

        Random r = new Random();
        // 生成新的扰动坐标
        theta = r.nextDouble()*2*Math.PI;
        // 扰动的半径大小
        p = r.nextDouble();
        while (true) {
            radius = 1 / (1 - (1 + Math.exp(1) * p)*Math.exp(-eps*p));
            if (radius < 5) break;
        }

        // 乘以0.001限制其扰动的范围，即原论文中的单位大小；
        location_dp[0] = location[0] + radius * Math.cos(theta) * 0.001;
        location_dp[1] = location[1] + radius * Math.sin(theta) * 0.001;


        return location_dp;

    }


}
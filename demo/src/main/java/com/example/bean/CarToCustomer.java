package com.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: pwz
 * @create: 2022/9/22 11:08
 * @Description: 无人车站点到客户的路线 只用于前台展示 路径规划不使用
 * @FileName: CarToCustomer
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarToCustomer {

    private Integer id;
    private int start; //起始无人车站点
    private int end; //到达用户地点
    private int distance;
}
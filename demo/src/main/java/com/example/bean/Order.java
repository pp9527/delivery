package com.example.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: pwz
 * @create: 2022/9/13 11:37
 * @Description:
 * @FileName: Order
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("order_record")
public class Order {

    private Integer id;
    private int model;
    private String startStation;
    private double desLongitude;
    private double desLatitude;
    private int orderId;
    private String consignee;
    private int length;
    private int width;
    private int height;
    private double weight;
    private String goods;
    private Date deadline;
    private int status; // 默认0:未发货  1:运输中  2:超时  3:已完成
    private String info;
    private boolean privacyStatus; //隐私保护状态 启用为1

}
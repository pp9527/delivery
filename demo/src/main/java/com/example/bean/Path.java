package com.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: pwz
 * @create: 2022/9/16 15:37
 * @Description:
 * @FileName: Path
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Path {

    private Integer id;
    private int orderId;
    private int did;
    private int cid;
    private int stationNumber;
}
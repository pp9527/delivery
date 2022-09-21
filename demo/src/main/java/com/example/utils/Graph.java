package com.example.utils;


import com.example.bean.StationNetMap;
import com.example.service.StationNetMapService;
import com.example.service.impl.StationNetMapServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/20 9:44
 * @Description:
 * @FileName: Graph
 */
@Component
public class Graph {
    private static List<List<StationNetMap>> edges;// 每个顶点下的边的集合
    private static double[] minDis;// 源点到其他各点的最短距离
    private static int[] pre;// 寻找最短路径时存放前驱节点下表的数组


//    获取非静态接口
    public static Graph graph;

    @PostConstruct
    public void init() {
        graph = this;
    }

    @Resource
    StationNetMapService netMapService;

    public static List<List<StationNetMap>> getMaps() {
        edges = graph.netMapService.getMapData();
        return edges;
    }

}
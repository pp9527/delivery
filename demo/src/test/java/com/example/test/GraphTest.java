package com.example.test;

import com.example.utils.Graph;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @author: pwz
 * @create: 2022/9/26 16:13
 * @Description:
 * @FileName: testGraph
 */
@SpringBootTest
public class GraphTest {

    @Test
    public void testGetSequenceByName() {
        System.out.println(Graph.getSequenceByName("C3"));
    }

    @Test
    public void testGetMatrix() {
        int[][] matrix = Graph.getMatrix();
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + ", ");
            }
            System.out.println();
        }
    }

    @Test
    public void testStationNamesToLocations() {
        System.out.println(Graph.stationNamesToLocations(Arrays.asList("W1", "D1", "C1", "U1")));
    }

    @Test
    public void testGetVertex() {
        System.out.println(Graph.getVertex());
        for (String vertex : Graph.getVertex()) {
            System.out.print("\"" + vertex + "\",");
        }
    }
}
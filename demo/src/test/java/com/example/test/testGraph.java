package com.example.test;

import com.example.utils.Graph;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: pwz
 * @create: 2022/9/26 16:13
 * @Description:
 * @FileName: testGraph
 */
@SpringBootTest
public class testGraph {

    @Test
    public void testGetSequenceByName() {
        System.out.println(Graph.getSequenceByName("C3"));
    }
}
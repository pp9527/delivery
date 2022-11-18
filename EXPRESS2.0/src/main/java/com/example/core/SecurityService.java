package com.example.core;

import com.example.bean.Order;
import com.example.utils.OpenFaasUtils;
import com.example.utils.SecurityAlgorithmUtils;
import org.springframework.stereotype.Component;

/**
 * @author: pwz
 * @create: 2022/11/11 13:25
 * @Description:
 * @FileName: SecurityService
 */
@Component
public class SecurityService {

    public static Order getSafetyLocation(int type, Order order, double[] location) {
        // 是否请求openfaas服务 0：使用本地服务  1：请求openfaas
        int openFaasFlag = 1;
        if (type == 0) {
            // 关闭隐私保护
            order.setPrivacyLongitude(location[0]);
            order.setPrivacyLatitude(location[1]);
            order.setPrivacyStatus(false);
        } else {
            double[] enLocation = null; // 扰动后的坐标
            if (type == 1) {
                //使用SecurityAlgorithm.geoDp()方法加扰动
                enLocation = openFaasFlag == 0 ?
                        SecurityAlgorithmUtils.geoDp(location) : OpenFaasUtils.geoDp(location);
            } else if (type == 2) {
                //其他方法
                enLocation = openFaasFlag == 0 ?
                        SecurityAlgorithmUtils.geoDpEnhance(location) : OpenFaasUtils.geoDpEnhance(location);
            } else if (type == 3) {
                //其他方法
            }
            order.setPrivacyLongitude(enLocation[0]);
            order.setPrivacyLatitude(enLocation[1]);
            order.setPrivacyStatus(true);
        }
        return order;
    }
}
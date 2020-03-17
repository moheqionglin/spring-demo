package com.moheqionglin.dubbo.JavaExportConsumerService.manualExportService.zk;

import com.moheqionglin.dubbo.JavaExportConsumerService.Utils;
import com.moheqionglin.dubbo.service.Calculator;
import com.moheqionglin.dubbo.service.CalculatorImpl;

/**
 * @author wanli.zhou
 * @description
 * @time 09/11/2018 5:27 PM
 */
public class DubboServer2 {

    public static void main(String[] args) {
        Utils.exportServiceWithZk(Calculator.class, new CalculatorImpl(), "xxx", "127.0.0.1:2181", 9999, "group1", "v1");
        System.out.println();

        System.out.println();
        System.out.println();

        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
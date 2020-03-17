package com.moheqionglin.dubbo.JavaExportConsumerService.manualExportService.zk;

import com.moheqionglin.dubbo.JavaExportConsumerService.Utils;
import com.moheqionglin.dubbo.service.Calculator;
import com.moheqionglin.dubbo.service.CalculatorImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 09/11/2018 5:27 PM
 */
public class DubboServer {


    public static void main(String[] args) {
        Utils.exportServiceWithZk(Calculator.class, new CalculatorImpl(), "xxx", "127.0.0.1:2181", 9998, "group1", "v1");
        System.out.println();

        System.out.println();
        System.out.println();
        List list = new ArrayList();



        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
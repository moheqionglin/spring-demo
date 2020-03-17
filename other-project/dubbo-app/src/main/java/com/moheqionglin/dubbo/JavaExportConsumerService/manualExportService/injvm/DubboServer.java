package com.moheqionglin.dubbo.JavaExportConsumerService.manualExportService.injvm;

import com.moheqionglin.dubbo.JavaExportConsumerService.Utils;
import com.moheqionglin.dubbo.service.Calculator;
import com.moheqionglin.dubbo.service.CalculatorImpl;

/**
 * @author wanli.zhou
 * @description
 * @time 09/11/2018 5:27 PM
 */
public class DubboServer {

    public static void main(String[] args) {

        Utils.exportServiceInJVM(Calculator.class, new CalculatorImpl(), "xxx", 9998, "127.0.0.1:2181", "group1", "v1");
        DubboConsumer.main(null);
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
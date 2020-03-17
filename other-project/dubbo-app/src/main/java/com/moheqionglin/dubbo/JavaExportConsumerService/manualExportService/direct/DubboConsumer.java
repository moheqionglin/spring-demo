package com.moheqionglin.dubbo.JavaExportConsumerService.manualExportService.direct;

import com.alibaba.dubbo.rpc.RpcContext;
import com.moheqionglin.dubbo.JavaExportConsumerService.Utils;
import com.moheqionglin.dubbo.service.Calculator;

/**
 * @author wanli.zhou
 * @description
 * @time 19/11/2018 1:53 PM
 */
public class DubboConsumer {

    public static void main(String[] args) {
        //Class<T> clazz, String appName, String group, String ip, Integer port, String version
        Calculator calc = Utils.getReferenceP2PDirect(Calculator.class,
                "xxx",
                "group1",
                "127.0.0.1",
                8899, "v1");

        RpcContext.getContext().setAttachment("test", "===>");
        // 和本地bean一样使用xxxService

        System.out.println(calc.add(1, 4));
    }
}
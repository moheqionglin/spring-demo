package com.moheqionglin.dubbo.JavaExportConsumerService.manualExportService.zk;

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
        consumerp2pDirect();
    }

    private static void consumerp2pDirect() {
        // , String ip, Integer port, String version
        Calculator calc = Utils.getReferenceFromZk(Calculator.class, "xxx", "127.0.0.1:2181", "group1",  "v1", false);

        RpcContext.getContext().setAttachment("test", "===>");
        // 和本地bean一样使用xxxService

        System.out.println(calc.add(1, 4));
    }

    private static void consumerFromZk() {
        Calculator calc = Utils.getReferenceFromZk(Calculator.class, "xxx", "127.0.0.1:2181",
                "group1", "v1", false);

        RpcContext.getContext().setAttachment("test", "===>");
        // 和本地bean一样使用xxxService

        System.out.println(calc.add(1, 4));
    }


}
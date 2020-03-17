package com.moheqionglin.dubbo.JavaExportConsumerService.manualExportService.injvm;

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

        Calculator calc = Utils.getReferenceInJVM(Calculator.class, "xxx", "group1","v1", false);

        RpcContext.getContext().setAttachment("test", "===>");
        // 和本地bean一样使用xxxService

        System.out.println(calc.add(1, 4));
    }
}
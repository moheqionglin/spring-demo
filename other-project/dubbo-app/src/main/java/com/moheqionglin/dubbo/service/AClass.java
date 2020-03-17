package com.moheqionglin.dubbo.service;

import com.alibaba.dubbo.rpc.RpcContext;

/**
 * @author wanli.zhou
 * @description
 * @time 23/11/2018 5:14 PM
 */
public class AClass {

    public void print(){
        System.out.println("Aclass" + RpcContext.getContext().getAttachment("test"));
//        new BClass().print();
    }
}
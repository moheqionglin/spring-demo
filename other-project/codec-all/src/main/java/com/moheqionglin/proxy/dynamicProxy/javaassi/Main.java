package com.moheqionglin.proxy.dynamicProxy.javaassi;

import com.moheqionglin.proxy.CalcImpl;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-30 17:03
 */
public class Main {
    //javaassitent 不需要 父接口
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        JavaAssistProxyFactory proxyFactory = new JavaAssistProxyFactory();
        CalcImpl proxy = proxyFactory.crateProxy(CalcImpl.class);
        System.out.println(proxy.add(1, 2));
    }
}
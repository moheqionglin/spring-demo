package com.moheqionglin.proxy.dynamicProxy.cglib;

import com.moheqionglin.proxy.CalcImpl;
import com.moheqionglin.proxy.dynamicProxy.javaassi.JavaAssistProxyFactory;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-30 17:15
 */
public class Main {
    //cglib 不需要 父接口
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        CglibProxyFactory proxyFactory = new CglibProxyFactory();
        CalcImpl proxy = proxyFactory.createProxy(CalcImpl.class);
        System.out.println(proxy.add(1, 2));
    }
}
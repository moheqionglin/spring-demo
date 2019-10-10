package com.moheqionglin.proxy.dynamicProxy.jdk;

import com.moheqionglin.proxy.Calc;
import com.moheqionglin.proxy.CalcImpl;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-30 16:39
 */
public class Main {
    public static void main(String[] args) {
        CalcImpl target = new CalcImpl();

        JdkProxyFactory proxyFactory = new JdkProxyFactory();

        Calc proxy = (Calc)proxyFactory.createProxy(target);


        System.out.println(proxy.add(1, 2));
    }
}
package com.moheqionglin.proxy.staticProxy;

import com.moheqionglin.proxy.CalcImpl;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-30 16:32
 */
public class Main {
    public static void main(String[] args) {
        CalcImpl target = new CalcImpl();
        //设置target的代理
        CalcStaticProxy proxy = new CalcStaticProxy(target);

        System.out.println(proxy.add(1, 2));
    }
}
package com.moheqionglin.proxy.dynamicProxy.javaassi;


import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-30 16:51
 */

public class JavaAssistProxyFactory {

    //javaassist不需要 接口
    public<T> T crateProxy(Class<T> targetClass) throws IllegalAccessException, InstantiationException {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(targetClass);
        proxyFactory.setHandler(new MethodHandler() {
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                System.out.println("javaassit 动态代理 biz logic");
                return proceed.invoke(self, args);
            }
        });
        return (T)proxyFactory.createClass().newInstance();
    }
}
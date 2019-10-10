package com.moheqionglin.proxy.dynamicProxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-30 17:06
 */
public class CglibProxyFactory {
    public<T> T createProxy(Class<T> targetClass){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new MethodInterceptor(){
            @Override
            public Object intercept(Object proxy, Method targetMethod, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("cglib proxy biz logic ");
                return methodProxy.invokeSuper(proxy, args);
            }
        });
        return (T)enhancer.create();
    }
}
package com.moheqionglin.proxy.dynamicProxy.jdk;

import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-30 16:34
 */
public class JdkProxyFactory {


    public <T> Object createProxy(T target){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new MyInvocationHandler(target));
    }

    class MyInvocationHandler<T> implements InvocationHandler {

        private final T target;

        public MyInvocationHandler(T target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("jdk dynamic proxy process logic");
            return method.invoke(target, args);
        }
    }
}
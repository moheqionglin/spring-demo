package com.moheqionglin.rpc.client;

import com.moheqionglin.rpc.rpcInterface.Calculator;
import sun.reflect.Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-04-11 13:49
 */
public class PRCProxy {

    static class RPCInvoker {
        private String className = "";
        private String methodName = "";
        private Object[] args;

        public RPCInvoker(String className, String methodName, Object[] args) {
            this.className = className;
            this.methodName = methodName;
            this.args = args;
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {


        Calculator c = (Calculator) Proxy.newProxyInstance(Calculator.class.getClassLoader(), new Class[]{Calculator.class}, new InvocationHandler(){

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RPCInvoker ri = new RPCInvoker(method.getDeclaringClass().toGenericString(), method.getName(), args);
                System.out.println(method.getName() + "\n" + proxy.getClass());
                System.out.println("--->");
                return 2;
            }
        });

        System.out.println(c.add(1, 2));


    }


}
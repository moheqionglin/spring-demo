package com.moheqionglin.rpc.dynamicproxy;

import com.moheqionglin.rpc.rpcInterface.Calculator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;

public class DP {
    public static void main(String[] args) {
        Calculator calculatorImpl = (Calculator) Proxy.newProxyInstance(DP.class.getClassLoader(), new Class[]{Calculator.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method + "" + args);
                if(method.getName().equals("add")
                        && Arrays.equals(method.getParameterTypes(), new Class[]{int.class, int.class})
                        && method.getReturnType() == int.class){
                    System.out.println("impl add method");
                    return (Integer.valueOf(args[0].toString()) + Integer.valueOf(args[1].toString()));
                }else if(method.getName().equals("multiple")
                        && Arrays.equals(method.getParameterTypes(), new Class[]{int.class, double.class})
                        && method.getReturnType() == double.class){
                    System.out.println("impl multiple method");
                    return (Integer.valueOf(args[0].toString()) * Double.valueOf(args[1].toString()));
                }
                return null;
            }
        });
        System.out.println(calculatorImpl.add(1, 2));
        System.out.println(calculatorImpl.multiple(1, 2.3));
    }
}

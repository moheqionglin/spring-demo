package com.moheqionglin.rpc.lilun;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-29 14:09
 */
public class GetMethod {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MyServiceI1 myServiceI1 = new MyServiceI1();

        String methodName = "add";
        Class[] types = new Class[]{int.class, int.class, double.class};

        Method addMethod = myServiceI1.getClass().getMethod(methodName, types);
        Object rst = addMethod.invoke(myServiceI1, 1, 2, 3d);
        System.out.println("----" + rst);
    }

}
interface MyService1{
    public int add(int a, int b, double c);
    public int add(int a, int b);

}

class MyServiceI1 implements MyService1{

    @Override
    public int add(int a, int b, double c) {
        return a + b + (int)c;
    }

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
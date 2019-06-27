package com.moheqionglin.alth.reflect;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-04-13 11:15
 */
public class Test {

    static class A {

        @CallerSensitive
        public void doSth(){
            new B().doStr();
        }
    }

    static class B {
        public void doStr(){
            System.out.println("--B---");
            System.out.println(Reflection.getCallerClass());
        }
    }
    @CallerSensitive
    public static void main(String[] args) {
       new Thread(()->{
           new A().doSth();
       }).start();
    }

}
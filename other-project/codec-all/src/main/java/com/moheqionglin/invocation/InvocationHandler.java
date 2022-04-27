package com.moheqionglin.invocation;

import org.apache.hadoop.fs.shell.Count;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class InvocationHandler {



    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InterruptedException {
        Random random = new Random();
        Invocation invocation = new Invocation(random, random.getClass().getMethod("nextInt", new Class[]{int.class}), new Object[]{5});

        for(int i = 0; i < 5; i++){
            System.out.println(invocation.doInvocation());
        }




    }
}

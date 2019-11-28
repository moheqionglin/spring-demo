package com.moheqionglin.aqs;

import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-27 10:01
 */
public class VolatileLearn {

    //不加volatile的话 不同线程对map是不可见的
    private static HashMap<String, String> map = new HashMap<>();

    private static void memoryVisiableTest() throws InterruptedException {
        new Thread(()->{
            for(;;){
                if("v1".equalsIgnoreCase(map.get("k1"))){
                    break;
                }
            }
            System.out.println(map);
        }).start();

        Thread.sleep(1000);
        new Thread(()->{
            map.put("k1", "v1");
            System.out.println("put k1 v1");
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        memoryVisiableTest();
    }


    private volatile static int shareState = 0;
    private static void memoryVisiableTest2() throws InterruptedException {
        Runnable runnable = () ->{
            int i = 0, j = 0;
            for ( ; shareState < 10000; ){
                int before = shareState ++;
                int after = shareState;
                if(before != after - 1){
                    i ++;
                }
                j ++;
            }
            System.out.println("i >> " + i);
            System.out.println("j >> " + j);
        };
        Thread t1 = new Thread(runnable);
        t1.start();

        Thread t2 = new Thread(runnable);
        t2.start();

        t1.join();
        t2.join();

    }
}
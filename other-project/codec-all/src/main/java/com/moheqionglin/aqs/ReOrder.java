package com.moheqionglin.aqs;

import java.util.concurrent.TimeUnit;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-28 13:45
 */
public class ReOrder {
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        for(int i =0; ;i ++){
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread t1 = new Thread(()->{
                sleep(10000);
                a = 1;
                x = b;
            });
            Thread t2 = new Thread(()->{
                b = 1;
                y = a;
            });
            t1.start();
            t2.start();
            t1.join();
            t2.join();

            System.out.println("第 " + i + "次 ( " + x + ", " + y + ")");
            if(x == 0 && y == 0){
                break;
            }
        }

    }

    private static void sleep(int t) {
        try {
            TimeUnit.NANOSECONDS.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
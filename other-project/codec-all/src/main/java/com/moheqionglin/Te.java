package com.moheqionglin;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-07 15:23
 */
public class Te {
     static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Double a = new Double(2.3);
        System.out.println(a);

    }

    public void flush()  {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getId());
    }


}
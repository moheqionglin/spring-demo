package com.moheqionglin.jcu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-06-06 13:50
 *
 * 两个线程的线程池， 如果提交三个任务， 前两个 分别是 sleep 10s， 那么 前两个任务会一直占用，第三个任务无法执行。
 */
public class ThreadPoolWithSleep {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() ->{
            try {
                for(int i = 0 ; i < 1000; i ++){
                    System.out.println("1 stat " + Thread.currentThread().getName());
                    Thread.sleep(10 * 1000);
                    System.out.println("1 end " + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() ->{
            try {
                for(int i = 0 ; i < 1000; i ++) {
                    System.out.println("2 stat " + Thread.currentThread().getName());
                    Thread.sleep(10 * 1000);
                    System.out.println("2 end " + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() ->{
            try {
                for(int i = 0 ; i < 1000; i ++) {
                    System.out.println("3 stat " + Thread.currentThread().getName());
                    Thread.sleep(10 * 1000);
                    System.out.println("3 end " + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

    }


}
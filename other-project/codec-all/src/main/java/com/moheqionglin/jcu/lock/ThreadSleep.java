package com.moheqionglin.jcu.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-29 14:11
 */
public class ThreadSleep {
    static AtomicInteger id = new AtomicInteger(0);
    public static Object objLock = new Object();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1), (r, b) -> System.out.println("reject " + r));
        //验证 object.wait主动释放锁
        Runnable objectWaitRunnable = () -> {
            System.out.println(id.incrementAndGet() + " start into objLock " + Thread.currentThread().getId());
                synchronized (objLock){
                    System.out.println(id.incrementAndGet() + " get lock ");
                    try {
                        Thread.sleep(1000 * 4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            System.out.println("end out objLock " + Thread.currentThread().getId());
        };
        for(int i = 0 ; i < 4; i ++){
            executorService.submit(objectWaitRunnable);
        }
    }


}
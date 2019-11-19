package com.moheqionglin.jcu.lock;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-29 13:54
 */
public class ObjectWait {
    public static Object objLock = new Object();
    static AtomicInteger id = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1), (r, b) -> System.out.println("reject " + r));
        //验证 object.wait主动释放锁
        Runnable objectWaitRunnable = () -> {
            System.out.println(id.incrementAndGet() + " start into objLock " + Thread.currentThread().getId());
            synchronized (objLock){
                System.out.println(id.incrementAndGet() + " get Lock " + Thread.currentThread().getId());
                try {
                    objLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("end out objLock " + Thread.currentThread().getId());
        };
        for(int i = 0 ; i < 4; i ++){
            executorService.submit(objectWaitRunnable);
        }
        Thread.sleep(2000);
        synchronized (objLock){
            objLock.notifyAll();
        }
    }

}
package com.moheqionglin.jcu.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-29 14:07
 */
public class ConditionAwait {
    public static Lock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    static AtomicInteger id = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1), (r, b) -> System.out.println("reject " + r));
        //验证 object.wait主动释放锁
        Runnable objectWaitRunnable = () -> {
            System.out.println(id.incrementAndGet() + " start into objLock " + Thread.currentThread().getId());
            try {
                lock.lock();
                System.out.println(id.incrementAndGet() + " get Lock " + Thread.currentThread().getId());
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            System.out.println("end out objLock " + Thread.currentThread().getId());
        };
        for(int i = 0 ; i < 4; i ++){
            executorService.submit(objectWaitRunnable);
        }
        Thread.sleep(2000);
        try {
            lock.lock();
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }
}
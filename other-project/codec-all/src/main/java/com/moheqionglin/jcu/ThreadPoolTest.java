package com.moheqionglin.jcu;

import java.util.concurrent.*;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-03 18:26
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                100L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("reject ");
            }
        });

        threadPoolExecutor.submit(()->{
            System.out.println(" " + Thread.currentThread().getId());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPoolExecutor.submit(()->{
            System.out.println("xxxxx");
        });
        threadPoolExecutor.submit(()->{
            System.out.println("xxxxx");
        });
        threadPoolExecutor.shutdown();

    }
}
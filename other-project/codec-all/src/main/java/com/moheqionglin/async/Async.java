package com.moheqionglin.async;

import java.util.concurrent.CompletableFuture;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-28 10:08
 */
public class Async {
    public static void main(String[] args) {
        System.out.println(1);
        long start = System.currentTimeMillis();

        CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("->>" + Thread.currentThread().getId());

            return "async-1";
        }).thenAcceptAsync(r -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务 apply 开始" + Thread.currentThread().getId());
        }).whenComplete((r, e) -> {
            System.out.println(Thread.currentThread().getId() );
        }).join();

        System.out.println("end" + (System.currentTimeMillis() - start));
    }

}
package com.moheqionglin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadPool {

    static final Runnable task = () -> {
        final AtomicInteger integer = new AtomicInteger(1);

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + " =》 Finish task " + integer.getAndAdd(1));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finish task");
        };


        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MINUTES,
                new SynchronousQueue(),  new ThreadPoolExecutor.CallerRunsPolicy());
        System.out.println("1");
        executorService.submit(task);
        System.out.println("2");
        executorService.submit(task);
        System.out.println("3");
        executorService.submit(task);
        System.out.println("4");
        executorService.submit(task);
        executorService.shutdown();
    };



    public static void main(String[] args) throws ExecutionException, InterruptedException {


        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<?> submit = executorService.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(30000);
                return "---" + Thread.currentThread().getName();
            }
        });
        System.out.println(submit.get());
        System.out.println(submit.get());
        System.out.println(submit.get());

        executorService.submit(task);
        executorService.shutdown();
    }
}

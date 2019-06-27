package com.moheqionglin.jcu;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-06-06 14:35
 */
public class CustomFuture {

    public static void main(String[] args) throws InterruptedException {
        Object a = "aa";
        System.out.println(a instanceof String);
        System.exit(1);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CustomTask customTask1 = new CustomTask("naem-1");
        CustomTask customTask2 = new CustomTask("naem-2");
        CustomTask customTask3 = new CustomTask("naem-3");
        executorService.submit(customTask1);
        executorService.submit(customTask2);
        executorService.submit(customTask3);

        Thread.sleep(10 * 1000);
        customTask1.setRun(false);
        Thread.sleep(2 * 1000);
        customTask1.setRun(true);

        Thread.sleep(2 * 1000);
        customTask2.setRun(false);
    }

    static class CustomTask implements Runnable {
        public boolean run = true;
        private String name = "";
        public CustomTask(String name) {
            this.name = name;
        }

        public void setRun(boolean run) {
            this.run = run;
        }

        @Override
        public void run() {
            while (run){
                System.out.println(name + " " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("exit");
        }
    }
}
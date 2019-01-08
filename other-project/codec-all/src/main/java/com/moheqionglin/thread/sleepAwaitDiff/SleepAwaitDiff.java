package com.moheqionglin.thread.sleepAwaitDiff;


/**
 * @author wanli.zhou
 * @description
 * @time 15/11/2018 2:24 PM
 */
public class SleepAwaitDiff {
    public static void main(String[] args) throws InterruptedException {
        testSleep();

    }
    public static void testAwait() throws InterruptedException {
        Object lock = new Object();
        new Thread(() -> {
            String name = Thread.currentThread().getName();

            System.out.println(" " + name + " 加锁前...");

            synchronized (lock){
                System.out.println(" " + name + " 加锁中...");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(" " + name + " 加锁后...");

        }).start();

        Thread.sleep(1000);

        new Thread(() -> {

            String name = Thread.currentThread().getName();
            System.out.println(name + " 加锁前...");

            synchronized (lock){
                System.out.println(name + " 加锁中...");
            }
            System.out.println(name + " 加锁后...");
        }).start();
    }

    public static void testSleep() throws InterruptedException {
        Object lock = new Object();
        new Thread(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(" " + name + " 加锁前...");
            synchronized (lock){
                System.out.println(" " + name + " 加锁中...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(" " + name + " 加锁后...");
        }).start();
        Thread.sleep(1000);
        new Thread(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " 加锁前...");
            synchronized (lock){
                System.out.println(name + " 加锁中...");
            }
            System.out.println(name + " 加锁后...");
        }).start();
    }
}
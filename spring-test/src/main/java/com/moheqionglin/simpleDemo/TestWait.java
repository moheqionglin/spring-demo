package com.moheqionglin.simpleDemo;

public class TestWait {
    public static void main(String[] args) {
        Runnable lock = () -> {
            synchronized (TestWait.class){
                System.out.println(" [lock] start await " + Thread.currentThread().getName());
                try {
                    TestWait.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(" [lock]  exit await " + Thread.currentThread().getName());
                }
            }
        };

        Runnable unlock = () -> {
            synchronized (TestWait.class){
                System.out.println(" [unlock]  start await " + Thread.currentThread().getName());
                TestWait.class.notifyAll();
                System.out.println(" [unlock]  exit await " + Thread.currentThread().getName());
            }
        };

        for (int i = 0; i < 5; i++) {
            new Thread(lock).start();
        }

        new Thread(unlock).start();
    }
}

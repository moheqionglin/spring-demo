package com.moheqionglin.collection;

/**
 * @author wanli.zhou
 * @description
 * @time 02/01/2019 4:25 PM
 */
public class HashMapTest {

    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        new Thread(() -> {
            String tname = Thread.currentThread().getName();
            synchronized (lock1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(tname + " get lock1 ");
                synchronized (lock2){
                    System.out.println(tname + " get lock2 ");
                }
            }
        }).start();
        new Thread(() -> {
            String tname = Thread.currentThread().getName();
            synchronized (lock2){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(tname + " get lock2 ");
                synchronized (lock1){
                    System.out.println(tname + " get lock1 ");
                }
            }
        }).start();
    }
}
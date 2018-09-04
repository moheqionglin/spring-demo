package com.moheqionglin;

import java.util.concurrent.TimeUnit;

public class AwaitTest {

    public static void main(String[] args) throws InterruptedException {
        AwaitTest awaitTest = new AwaitTest();
        awaitTest.notifyAllTest();

    }



    public void notifyAllTest() throws InterruptedException {
        Count count = new Count();
        start4Thread(count);


        TimeUnit.SECONDS.sleep(3);
        System.out.println("Start notify all");
        synchronized (count){
            count.notifyAll();
        }

    }



    public void notifyTest() throws InterruptedException {
        Count count = new Count();
        start4Thread(count);


        TimeUnit.SECONDS.sleep(3);
        System.out.println("Start notify a thread");
        synchronized (count){
            count.notify();
        }

    }

    private void start4Thread(Count count ) {


        MyThread t1 = new MyThread(count);
        MyThread t2 = new MyThread(count);
        MyThread t3 = new MyThread(count);
        MyThread t4 = new MyThread(count);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    class MyThread extends Thread{
        Count count ;

        public MyThread(Count count){
            this.count = count;
        }

        @Override
        public void run() {

            Long id = Thread.currentThread().getId();
            System.out.println("Thread " + id + " start..");

            synchronized (count){
                try {
                    count.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Thread " + id + " finish");

        }
    }

    class Count{
        public volatile int count = 2;
    }
}

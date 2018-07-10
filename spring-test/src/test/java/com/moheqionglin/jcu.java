package com.moheqionglin;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class jcu {
    public static void main1(String[] args) {

        final Semaphore semaphore = new Semaphore(1);
        ReentrantLock reentrantLock = new ReentrantLock();

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(){
            @Override
            public void run() {
                int i = 0;
                for(;;){
                    try {
                        cyclicBarrier.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i += 2;
                }


            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                int i = 1;
                for(;;){
                    try {
                        cyclicBarrier.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(i);
                    i += 2;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }


    public static void main(String[] args) {
        final Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(){
            @Override
            public void run() {
                Integer i = 0;
                for(;;){

                    System.out.println(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        i =  exchanger.exchange(i) + 1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                Integer i = 0;
                for(;;){
                    try {
                        i =  exchanger.exchange(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(i == 0){
                        continue;
                    }
                    System.out.println(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();


    }
}

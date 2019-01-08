//package com.moheqionglin.jcu;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author wanli.zhou
// * @description
// * @time 27/12/2018 10:25 PM
// */
//public class CountLaunchDownTest {
//
//    public static void main(String[] args) throws InterruptedException {
//        CountDownLatch countDownLatch = new CountDownLatch(2);
//
//
//        new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + " Before count down.");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            countDownLatch.countDown();
//
//            System.out.println(Thread.currentThread().getName() + " After count down.");
//        }).start();
//
//
//        new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + " Before count down.");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            countDownLatch.countDown();
//            System.out.println(Thread.currentThread().getName() + " After count down.");
//        }).start();
//
//        new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + " Before count down.");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            countDownLatch.countDown();
//            System.out.println(Thread.currentThread().getName() + " After count down.");
//        }).start();
//
//
//        new Thread(() -> {
//            try {
//                countDownLatch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName() + " After count await.");
//        }).start();
//
//
//        Thread.sleep(100000);
//    }
//}
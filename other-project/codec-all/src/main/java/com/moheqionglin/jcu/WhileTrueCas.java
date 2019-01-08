//package com.moheqionglin.jcu;
//
//import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.concurrent.atomic.AtomicReference;
//
///**
// * @author wanli.zhou
// * @description
// * @time 02/01/2019 11:05 AM
// */
//public class WhileTrueCas {
//
//
//    public static void main(String[] args) {
//        ReEntrantLockTest2();
//    }
//
//    private static void noEntrantLockTest2() {
//
//        NonEntrantLock nonEntrantLock = new NonEntrantLock();
//        final Counter counter = new Counter() ;
//        Runnable NonEntrantLockRunnable = () -> {
//           String name = Thread.currentThread().getName();
//           while (true){
//               nonEntrantLock.lock();
//               for(int end = counter.i + 5; counter.i < end; counter.i++){
//                   try {
//                       Thread.sleep(500);
//                   } catch (InterruptedException e) {
//                       e.printStackTrace();
//                   }
//                   System.out.println("[" +name + "] " + counter.i);
//               }
//               nonEntrantLock.lock();
//               for(int end = counter.i + 5; counter.i < end; counter.i++){
//                   try {
//                       Thread.sleep(500);
//                   } catch (InterruptedException e) {
//                       e.printStackTrace();
//                   }
//                   System.out.println("[" +name + "] " + counter.i);
//               }
//               nonEntrantLock.unlock();
//               nonEntrantLock.unlock();
//           }
//       };
//
//        new Thread(NonEntrantLockRunnable).start();
//        new Thread(NonEntrantLockRunnable).start();
//    }
//
//    private static void noEntrantLockTest1() {
//        NonEntrantLock nonEntrantLock = new NonEntrantLock();
//        final Counter counter = new Counter() ;
//        Runnable NonEntrantLockRunnable = () -> {
//            String name = Thread.currentThread().getName();
//            while (true){
//                nonEntrantLock.lock();
//                for(int end = counter.i + 5; counter.i < end; counter.i++){
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("[" +name + "] " + counter.i);
//                }
//                nonEntrantLock.unlock();
//            }
//        };
//
//        new Thread(NonEntrantLockRunnable).start();
//        new Thread(NonEntrantLockRunnable).start();
//    }
//
//    private static void ReEntrantLockTest2() {
//        ReEntrantLock ReEntrantLock = new ReEntrantLock();
//        final Counter counter = new Counter() ;
//        Runnable ReEntrantLockRunnable = () -> {
//            String name = Thread.currentThread().getName();
//            while (true){
//                ReEntrantLock.lock();
//                for(int end = counter.i + 5; counter.i < end; counter.i++){
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("[" +name + "] " + counter.i);
//                }
//                ReEntrantLock.lock();
//                for(int end = counter.i + 5; counter.i < end; counter.i++){
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("[" +name + "] " + counter.i);
//                }
//                ReEntrantLock.unlock();
//                ReEntrantLock.unlock();
//            }
//        };
//
//        new Thread(ReEntrantLockRunnable).start();
//        new Thread(ReEntrantLockRunnable).start();
//    }
//
//    private static void ReEntrantLockTest1() {
//        ReEntrantLock ReEntrantLock = new ReEntrantLock();
//        final Counter counter = new Counter() ;
//        Runnable ReEntrantLockRunnable = () -> {
//            String name = Thread.currentThread().getName();
//            while (true){
//                ReEntrantLock.lock();
//                for(int end = counter.i + 5; counter.i < end; counter.i++){
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("[" +name + "] " + counter.i);
//                }
//                ReEntrantLock.unlock();
//            }
//        };
//
//        new Thread(ReEntrantLockRunnable).start();
//        new Thread(ReEntrantLockRunnable).start();
//    }
//
//    static class ReEntrantLock {
//        //false 表示没人获取锁，true表示有人获取锁
//        AtomicReference<Long> lockThread = new AtomicReference<>(0L);
//        int lockCount = 0;
//
//        public void lock(){
//            //只有当没有人获取锁的时候，我们把获取锁
//            long id = Thread.currentThread().getId();
//            if(lockThread.get().compareTo(id) == 0){
//                lockCount ++;
//                return;
//
//            }
//            while (!lockThread.compareAndSet(0L, id)){
//            }
//            lockCount++;
//        }
//
//        public void unlock(){
//            //只有当没有人获取锁的时候，我们把获取锁
//            if(lockCount > 1){
//                lockCount--;
//                return;
//            }
//            lockThread.compareAndSet(Thread.currentThread().getId(), 0L);
//            lockCount = 0;
//
//        }
//    }
//
//    static class NonEntrantLock {
//        //false 表示没人获取锁，true表示有人获取锁
//        AtomicBoolean lockMark = new AtomicBoolean(false);
//
//        public void lock(){
//            //只有当没有人获取锁的时候，我们把获取锁
//            while(!lockMark.compareAndSet(false, true)){
//            }
//        }
//
//        public void unlock(){
//            //只有当没有人获取锁的时候，我们把获取锁
//            lockMark.compareAndSet(true, false);
//        }
//    }
//
//    static class Counter{
//        public int i = 0;
//    }
//
//}
package com.moheqionglin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {


    public static void main1(String[] args) {

            final Object lock1 = new Object();
            final Object lock2 = new Object();
            boolean a = false;
            new Thread(){
                @Override
                public void  run() {
                    long tid = Thread.currentThread().getId();
                    System.out.println(tid + " start work.");
                    synchronized(lock1){
                        try {
                            lock1.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println(tid + " pass 1 start work.");
                        try {
                            TimeUnit.SECONDS.sleep(2l);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        synchronized(lock2){
                            lock2.notifyAll();
                            System.out.println();
                        }
                    }

                    System.out.println(tid + " pass 2 start work.");

                }
            }.start();

            new Thread(){
                @Override
                public void run() {
                    long tid = Thread.currentThread().getId();
                    System.out.println(tid + " start work.");
                    synchronized(lock1){
                        try {
                            lock1.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println(tid + " pass 1 start work.");
                        try {
                            TimeUnit.SECONDS.sleep(2l);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        synchronized(lock2){
                            lock2.notifyAll();
                        }
                        System.out.println(tid + " pass 2 start work.");
                    }

                }
            }.start();
    }


    public static void main2(String[] args) {

        final Object lock1 = new Object();
        final Object lock2 = new Object();
        boolean a = false;
        new Thread(){
            @Override
            public void  run() {
                long tid = Thread.currentThread().getId();
                System.out.println(tid + " start work.");
                synchronized(lock1){
                    try {
                        lock1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
                System.out.println(tid + " pass 1 start work.");
                try {
                    TimeUnit.SECONDS.sleep(2l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized(lock2){
                    lock2.notifyAll();
                    System.out.println();
                }
                System.out.println(tid + " pass 2 start work.");

            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                long tid = Thread.currentThread().getId();
                System.out.println(tid + " start work.");
                synchronized(lock1){
                    try {
                        lock1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(tid + " pass 1 start work.");

                }
                try {
                    TimeUnit.SECONDS.sleep(2l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized(lock2){
                    lock2.notifyAll();
                }
                System.out.println(tid + " pass 2 start work.");

            }
        }.start();
    }


    public static void main3(String[] args) {
        final Object lock1 = new Object();
        final Object lock2 = new Object();


        new Thread(){
            @Override
            public void run() {
                long tid = Thread.currentThread().getId();
                System.out.println(tid + " start work.");

                synchronized(lock1){
                    System.out.println(tid + " pass 1 start work.");
                    for(int i = 0 ; i < 1000; i++){
                         Math.atan2(i , i);
                    }
                    synchronized (lock2){

                    }
                    System.out.println(tid + " pass 2 start work.");
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                long tid = Thread.currentThread().getId();
                System.out.println(tid + " start work.");

                synchronized(lock2){
                    System.out.println(tid + " pass 1 start work.");
                    for(int i = 0 ; i < 1000; i++){
                        Math.atan2(i , i);
                    }
                    synchronized (lock1){

                    }
                    System.out.println(tid + " pass 2 start work.");
                }
            }
        }.start();

    }


    public static void main5(String[] args) {
        final ReentrantLock lock1 = new ReentrantLock();
        final ReentrantLock lock2 = new ReentrantLock();


        new Thread(){
            @Override
            public void run() {
                long tid = Thread.currentThread().getId();
                System.out.println(tid + " start work.");

                lock1.lock();
                try{

                    for(int i = 0 ; i < 1000; i++){
                        Math.atan2(i , i);
                    }
                    System.out.println(tid + " pass 1 start work.");


                    lock2.lock();
                    try{

                    }finally {
                        lock2.unlock();
                    }
                    System.out.println(tid + " pass 2 start work.");

                }finally {
                    lock1.unlock();
                }


            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                long tid = Thread.currentThread().getId();
                System.out.println(tid + " start work.");

                lock2.lock();
                try{

                    for(int i = 0 ; i < 1000; i++){
                        Math.atan2(i , i);
                    }
                    System.out.println(tid + " pass 1 start work.");


                    lock1.lock();
                    try{

                    }finally {
                        lock1.unlock();
                    }
                    System.out.println(tid + " pass 2 start work.");

                }finally {
                    lock2.unlock();
                }
            }
        }.start();

    }


    public static void main(String[] args) {
        final List<BigObject> bos = Collections.synchronizedList(new ArrayList<BigObject>());
        new Thread(){
            @Override
            public void run() {
                for (;;){
                    bos.add(createObj());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                for(;;){
                    for( Iterator<BigObject> iterator = bos.iterator(); iterator.hasNext();){
                        BigObject bo = iterator.next();
                        useObj(bo);
                        bo = null;
                        iterator.remove();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();

    }

    public static BigObject createObj(){
        return new BigObject();
    }

    public static void useObj(BigObject bo){
        if (bo == null){
            return;
        }
        bo.MB[0] = Byte.valueOf("1");
        bo.MB[bo.MB.length - 1] = Byte.valueOf("2");
    }

    static class BigObject{
        public byte[] MB = new byte[1024 * 1024];

    }

    /*
    *
    *
    *  -server
 -Xmx11165m -Xms11165m -Xmn5582m -Xss256k -XX:PermSize=256m -XX:LargePageSizeInBytes=128m
 -XX:+DisableExplicitGC
 -XX:+UseConcMarkSweepGC
 -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection
 -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly
 -XX:CMSInitiatingOccupancyFraction=70
-Dcom.sun.management.jmxremote.port=8087 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
-Xloggc:/opt/logs/gc.log  -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=20m
-XX:ErrorFile=/opt/logs/hotspot_err.log.%p
-XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=/opt/logs/%p.hprof


//http://itindex.net/detail/45942-jvm-%E8%87%B4%E5%91%BD%E9%94%99%E8%AF%AF-%E6%97%A5%E5%BF%97
https://www.cnblogs.com/langtianya/p/3898760.html
https://www.cnblogs.com/onmyway20xx/p/6590603.html

 -server
 -Xmx1000m -Xms100m -Xmn300m -Xss256k -XX:PermSize=300m
 -Xloggc:/Users/wanli.zhou/Desktop/gc.log  -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=20m

-server
-Xms128m -Xmx128m -XX:PermSize=64M -XX:MaxNewSize=32m -XX:MaxPermSize=64m
  -XX:+DisableExplicitGC  -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection
 -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly
 -XX:CMSInitiatingOccupancyFraction=70
-Xloggc:/Users/wanli.zhou/Desktop/gc.log  -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=20m


 -server   -Xmx1000m -Xms100m -Xmn300m -Xss256k -XX:PermSize=300m    -Xloggc:/Users/wanli.zhou/Desktop/gc.log  -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=20m 
    *
    * */
}

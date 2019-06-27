package com.moheqionglin.thread.threadSafe;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-04-26 18:24
 */
public class Test {

    public static void main(String[] args) {
        final List<String> data = Arrays.asList(new String[]{"A","B","C","D","E","F","G","H","I", "J"});
        final List<String> data2 = Arrays.asList(new String[]{"A2","B2","C2","D2","E2","F2","G2","H2","I2", "J2"});
        MyBiz myBiz = new MyBiz();

        Thread t1 = new Thread(()->{
            for(String s : data){
                myBiz.consumer(s);
            }
        });

        Thread t2 = new Thread(()->{
            for(String s : data2){
                myBiz.consumer(s);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myBiz.print();
    }

    static class MyBiz1 {
        static ConcurrentHashMap<Long, Vector<String>> cachedRecordsMap = new ConcurrentHashMap<>();
        ReentrantLock lock = new ReentrantLock();
        public void consumer(String data) {

            add(data);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (check()) {
                write();
            }
        }

        private void add(String data){
            lock.lock();
            try{
                Vector<String> rawList = cachedRecordsMap.get(1L);
                if (rawList == null) {
                    rawList = new Vector<>();
                    cachedRecordsMap.put(1L, rawList);
                }
                rawList.add(data);
            }finally {
                lock.unlock();
            }

        }

        private boolean check(){
            lock.lock();
            try{
                return cachedRecordsMap.get(1L)!= null && cachedRecordsMap.get(1L).size() > 2;
            }finally {
                lock.unlock();
            }

        }
        private void write() {
            lock.lock();
            try{
                Vector<String> rawList = cachedRecordsMap.put(1L, new Vector<>());
                System.out.println(Thread.currentThread().getId() + " " +rawList);
                rawList.clear();
            }finally {
                lock.unlock();
            }

        }
        public void print(){
            System.out.println(Thread.currentThread().getId() + " " + cachedRecordsMap.get(1L));
        }
    }

    static class MyBiz {
        static ConcurrentHashMap<Long, CopyOnWriteArrayList<String>> cachedRecordsMap = new ConcurrentHashMap<>();
        public void consumer(String data) {
            CopyOnWriteArrayList<String> rawList = cachedRecordsMap.get(1L);
            if (rawList == null) {
                rawList = new CopyOnWriteArrayList<>();
                cachedRecordsMap.put(1L, rawList);
            }
            rawList.add(data);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (rawList.size() >= 2) {
                write(rawList);
                rawList.clear();
            }
        }
        private void write(CopyOnWriteArrayList<String> rawList) {
            System.out.println(Thread.currentThread().getId() + " " +rawList);
        }
        public void print(){
            System.out.println(cachedRecordsMap.get(1L));
        }
    }

}

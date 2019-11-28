package com.moheqionglin.aqs;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-27 11:29
 */
public class ProducerAndConsumer {

    Queue<String> resources = new ArrayDeque<>(2);

    ReentrantLock lock = new ReentrantLock();
    Condition producerCon = lock.newCondition();
    Condition consumerCon = lock.newCondition();


    public static void main(String[] args) {
        new ProducerAndConsumer().run();
    }

    private boolean notFull() {
        return resources.size() < 2;
    }

    private boolean notEmpty(){
        return !resources.isEmpty();

    }

    private void run() {
        for(int i = 0 ; i < 10 ; i ++){
            new Thread(new Producer("msg-" + i)).start();
        }

        for(int i = 0 ; i < 10 ; i ++){
            new Thread(new Consumer()).start();
        }
    }


    class Consumer implements Runnable{

        @Override
        public void run() {
            lock.lock();
            if(resources.isEmpty()){
                try {
                    consumerCon.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getId() + " consumer > " + resources.poll() + ", " + resources.size());
            if(notFull()){
                producerCon.signal();
            }

            lock.unlock();

        }
    }

    class Producer implements Runnable{
        private final String res;

        public Producer(String res){
            this.res = res;
        }

        public String getRes() {
            return res;
        }

        @Override
        public void run() {
            lock.lock();

            if(resources.size() >= 2){
                try {
                    producerCon.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            resources.offer(this.res);
            System.out.println("\tproducer > " + this.res + ", " + resources.size());
            if(notEmpty()){
                consumerCon.signal();
            }


            lock.unlock();

        }
    }
}
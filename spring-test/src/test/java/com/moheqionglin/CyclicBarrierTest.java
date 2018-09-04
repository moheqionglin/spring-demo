package com.moheqionglin;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CyclicBarrierTest {

    public static void main(String[] args) {


        new CyclicBarrierTest().run();

    }

    public void run(){
        MyCyclicBarrier cyclicBarrier = new MyCyclicBarrier(2);
        First first = new First();

        MyThread t1 = new MyThread(0, cyclicBarrier, first);
        MyThread t2 = new MyThread(1, cyclicBarrier, first);
        t1.start();
        t2.start();
    }

    class First{
        public volatile boolean first = false;
    }
    class MyThread extends Thread{
        private int start ;
        private MyCyclicBarrier cyclicBarrier;
        First first;
        public MyThread(int start, MyCyclicBarrier cyclicBarrier, First first){
            this.start = start;
            this.cyclicBarrier = cyclicBarrier;
            this.first = first;
        }

        @Override
        public void run() {
            for(;;){
                synchronized (MyThread.class){
                    if(start == 0){
                        first.first = true;
                    }
                    if(!first.first){
                        try {
                            first.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println(start);
                cyclicBarrier.await();
                start += 2;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    class MyCyclicBarrier {

        private int count ;

        ReentrantLock lock = new ReentrantLock();

        Condition condition = lock.newCondition();

        MyCyclicBarrier(int c){
            this.count = c;
        }
        /**
         *
         * 思路， 每次调用await， 先判断 count 是否为0， 如果为0， 那么放行所有的thread， 然后继续--
         * */
        public void await(){
            lock.lock();
            try {
                count -= 1;
                if(count <= 0){
                    condition.signal();
                    condition.signal();
                    return;
                }
                condition.await();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }



        }



    }
}

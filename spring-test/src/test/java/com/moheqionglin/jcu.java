package com.moheqionglin;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class jcu {


    public static void main(String[] args) {

        jcu jcu = new jcu();
        jcu.run();
    }

    public void run(){
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        First first = new First();
        MyThread t1 = new MyThread(0, cyclicBarrier, first);
        MyThread t2 = new MyThread(1, cyclicBarrier, first);

        t1.start();
        t2.start();
    }


    class MyThread extends Thread{
        private int start ;
        private CyclicBarrier cyclicBarrier;
        First first;
        public MyThread(int start, CyclicBarrier cyclicBarrier, First first){
            this.start = start;
            this.cyclicBarrier = cyclicBarrier;
            this.first = first;
        }

        @Override
        public void run() {
            for(;;){
                System.out.println(start);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                start += 2;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class First{
        public volatile boolean first = false;
    }
}

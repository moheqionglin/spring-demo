package com.moheqionglin.blockthreadPool;

import java.util.concurrent.*;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-28 21:52
 */
public class BlockThreadPool {

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;
    public static void main(String[] args) {
        testThreadPool2();
//        System.out.println(RUNNING);
//        System.out.println(SHUTDOWN);
//        System.out.println(STOP);
//        System.out.println(TIDYING);
//        System.out.println(TERMINATED);
    }

    public static void testThreadPool1(){

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1,
                5L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new RejectedExecutionHandler(){

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                CustomRunnable runnable = (CustomRunnable) r;
                System.out.println("拒绝 任务 " + runnable.getData() + ". 当前线程池状态 activeThread=" + executor.getActiveCount() + ", task = " + executor.getQueue().size());

            }
        });
        threadPoolExecutor.execute(new CustomRunnable("data-1"));
        threadPoolExecutor.execute(new CustomRunnable("data-2"));
        threadPoolExecutor.shutdown();
    }

    public static void testThreadPool2(){

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1,
                5L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1), new RejectedExecutionHandler(){

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CustomRunnable runnable = (CustomRunnable) r;
                System.out.println("拒绝 任务 " + runnable.getData() + ". 当前线程池状态 activeThread=" + executor.getActiveCount() + ", task = " + executor.getQueue().size());

            }
        });
        threadPoolExecutor.execute(new CustomRunnable("data-1"));
        System.out.println(1);
        threadPoolExecutor.execute(new CustomRunnable("data-2"));
        System.out.println(2);
        threadPoolExecutor.execute(new CustomRunnable("data-3"));
        System.out.println(3);
        threadPoolExecutor.execute(new CustomRunnable("data-4"));
        System.out.println(4);
        threadPoolExecutor.shutdown();
    }

    static class CustomRunnable implements Runnable{
        private String data;

        public CustomRunnable(String data) {
            this.data = data;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(">>>>" + data + " " + (System.currentTimeMillis() - start) + " " + Thread.currentThread().getId());
        }

        public String getData() {
            return data;
        }
    }
}
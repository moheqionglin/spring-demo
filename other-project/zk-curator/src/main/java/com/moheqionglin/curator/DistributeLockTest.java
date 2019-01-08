package com.moheqionglin.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author wanli.zhou
 * @description
 * @time 28/12/2018 10:18 AM
 */
public class DistributeLockTest {

    private static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("127.0.0.1:2181")
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    private final static String LOCK_PATH = "/lock_path";


    public static void main(String[] args) {

        client.start();

        final InterProcessMutex lock = new InterProcessMutex(client, LOCK_PATH);
        Runnable runnable = () -> {
            try {
                lock.acquire();
                for ( int i = 0 ; i < 20 ; i ++){
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " -> " + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };

        new Thread(runnable).start();

        new Thread(runnable).start();

    }
}
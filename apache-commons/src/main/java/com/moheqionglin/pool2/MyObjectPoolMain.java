package com.moheqionglin.pool2;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.BufferedReader;
import java.io.StringBufferInputStream;
import java.util.concurrent.CountDownLatch;

public class MyObjectPoolMain {

    public static void main(String[] args) throws Exception {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();


        config.setMinIdle(3);
        config.setMaxIdle(4);
        //允许创建资源的最大数量，默认值 -1，-1 代表无数量限制（int类型）
        config.setMaxTotal(6);
        //默认值 true ，当资源耗尽时，是否阻塞等待获取资源
        config.setBlockWhenExhausted(true);
        //获取资源时的等待时间，单位毫秒。当 blockWhenExhausted 配置为 true 时，此值有效。 -1 代表无时间限制，一直阻塞直到有可用的资源。（long类型）
        config.setMaxWaitMillis(-1);
        //资源的存取数据结构，默认值 true，true 资源按照栈结构存取，false 资源按照队列结构存取
        config.setLifo(false);
        //当从池中获取资源或者将资源还回池中时 是否使用  java.util.concurrent.locks.ReentrantLock.ReentrantLock 的公平锁机制。 默认值 false， true 使用公平锁，false 不使用公平锁

        config.setFairness(true);
        //------ 资源回收策略配置
        //资源回收定时器 每隔0.5s 检查一次
        config.setTimeBetweenEvictionRunsMillis(1000);
        config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        //资源回收定时器每次 触发检查的对象个数
        config.setNumTestsPerEvictionRun(1);
        //每隔2s 清理没有用的对象
        config.setMinEvictableIdleTimeMillis(5000);

        config.setTestOnBorrow(true);
        config.setTestOnCreate(true);
        config.setTestOnReturn(true);
//        config.setTestWhileIdle(true);

        BookObjectPool objectPool = new BookObjectPool(new BookPooledObjectFactory(), config);
        Thread.sleep(5000);
        Book book = objectPool.borrowObject();
        System.out.println();
        objectPool.returnObject(book);
        //Returned object not currently part of this pool
//        objectPool.returnObject(new Book("aaa", 1));

//        objectPool.borrowObject();
//        objectPool.borrowObject();
//        objectPool.borrowObject();
//        objectPool.borrowObject();
//        objectPool.borrowObject();
//        objectPool.borrowObject();

        new CountDownLatch(1).await();

    }

}

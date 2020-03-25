package com.moheqionglin.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @ClassName : CuratorInit
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-24 10:24
 */
public class CuratorFactory {
    public static CuratorFramework getCuratorFramework(String namespace){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)  // 会话超时时间
                .connectionTimeoutMs(5000) // 连接超时时间
                .retryPolicy(retryPolicy)
                .namespace(namespace) // 包含隔离名称
                .build();
        client.start();
        return client;
    }
}
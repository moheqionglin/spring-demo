package com.moheqionglin.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Random;

/**
 * @author wanli.zhou
 * @description
 * @time 15/10/2018 5:33 PM
 */
public class LeaderSelectorTest1 {
    private final static String zk = "127.0.0.1:2181";

    public static void main(String[] args) throws InterruptedException {
        final CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(
                zk, 30000, 6000, new ExponentialBackoffRetry(1000, 3));
        String nodeIp = "127.0.0." + new Random(255).nextInt(254);
        String selectPath = "/leader";
        LeaderSelectorClient leaderSelectorClient = new LeaderSelectorClient(curatorFramework, nodeIp, selectPath);
//        leaderSelectorClient.start();


        Thread.sleep(Integer.MAX_VALUE);
    }
}
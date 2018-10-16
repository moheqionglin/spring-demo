package com.moheqionglin.curator.recipes.leaderselector;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wanli.zhou
 * @description
 * @time 11/10/2018 4:53 PM
 */
public class LeaderSelectorClient implements LeaderSelectorListener, Closeable {

    private final String nodeName;
    private final LeaderSelector leaderSelector;
    private final AtomicInteger leaderCount = new AtomicInteger();
    private AtomicBoolean isLeader = new AtomicBoolean(false);

    public LeaderSelectorClient(CuratorFramework curatorFramework, String nodeName, String path) {
        this.nodeName = nodeName;
        leaderSelector = new LeaderSelector(curatorFramework, path, this);
        // for most cases you will want your instance to requeue when it relinquishes leadership
        leaderSelector.setId(nodeName);
        leaderSelector.autoRequeue();
        leaderSelector.start();
    }
    public void start(){
        leaderSelector.start();
    }

    /**
     * 如果想要保住leader的地位，这个函数要一直运行下去，不能返回
     * @param curatorFramework
     * @throws Exception
     */
    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws InterruptedException {
        System.out.println("I am Leader " + nodeName);
        Thread.sleep(10000);
        isLeader.set(true);
//        for(;;){
//            try {
//                Thread.sleep(1*1000);
//            } catch (InterruptedException e) {
//                System.out.println("Leader " + nodeName + " thread Interrupt exception , maybe lose the leader.");
//            }finally {
//                System.out.println("Leader " + nodeName + " lose leader!!");
//            }
//        }
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        System.out.println("--->");
        if (connectionState == ConnectionState.LOST || connectionState == ConnectionState.SUSPENDED) {
            isLeader.set(false);
            try {
                System.out.println("Leader changes. Current leader is: " + leaderSelector.getLeader().getId());
            } catch (Exception e) {
                System.out.println("Error when get new leader" + e.getMessage());
            }
        }
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }
}
package com.moheqionglin.curator.leader;

import com.moheqionglin.curator.CuratorFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;

/**
 * @ClassName : LeaderCurator
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-24 15:47
 */
public class LeaderCurator {
    private CuratorFramework client = CuratorFactory.getCuratorFramework("demo");
    SynchronousQueue queue = new SynchronousQueue();
    LeaderSelector leaderSelector = null;
    private String ip = null;
    private boolean isLeader = false;
    public LeaderCurator(String ip) {
        this.ip = ip;
    }

    public boolean isLeader(){
        return isLeader;
    }

    public void selectLeader(){

        leaderSelector = new LeaderSelector(client, "/leader", new LeaderSelectorListener() {
            /**
             * 如果想要保持leader状态，那么这个函数不能返回
             * @param client
             * @throws Exception
             */
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println(ip + ":I am leader. start process leader biz logic");
                isLeader = true;
//                Thread.sleep(5000);
//                System.out.println(" > " + ip +" 在5秒以后放弃leader 状态！");
                new CountDownLatch(1).await();
            }

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                if (newState == ConnectionState.LOST || newState == ConnectionState.SUSPENDED) {
                    try {
                        System.out.println("Leader changes" );
                        queue.put(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        leaderSelector.setId(ip);
        leaderSelector.autoRequeue();
        leaderSelector.start();

        new Thread(()->{
            try {
                Object take = queue.take();
                System.out.println("new  Leader is " + leaderSelector.getLeader().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stop(){
        System.out.println(ip + " stoping...");
        leaderSelector.close();
    }
    public String getIp() {
        return ip;
    }
}
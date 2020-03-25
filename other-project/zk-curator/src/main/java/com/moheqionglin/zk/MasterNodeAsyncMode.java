package com.moheqionglin.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MasterNodeAsyncMode implements Watcher {

    private String server;
    private String nodeName;

    private ZooKeeper zooKeeper;
    private boolean isLeader = false;
    private final String MASTER_ZK_PATH = "/master";

    public MasterNodeAsyncMode(String server, String nodeName) {
        this.server = server;
        this.nodeName = nodeName;
    }
    public void startNode() throws IOException {
        zooKeeper = new ZooKeeper(server, 10000, this);
    }
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    /*
     *  @return 是否是leader
     *
     * 1. 我们尝试创建 /master， 同时在/master中保存当前机器的唯一标识
     *     1.1 如果 /master已经存在，那么创建失败
     *     1.2
     * */
    public void leaderSelection() {
        zooKeeper.create(MASTER_ZK_PATH,
                nodeName.getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                new AsyncCallback.StringCallback(){
                    // path 和 name只有在 create类型是 sequence的时候不一样，其他时候都一样
                    @Override
                    public void processResult(int resultCode, String path, Object ctx, String name) {
                        switch (KeeperException.Code.get(resultCode)){
                            case CONNECTIONLOSS:
                                checkForLeaderSelectionFinish();
                                break;
                            case OK:
                                initZkNodes(nodeName);
                                isLeader = true;
                                break;
                            default:
                                isLeader = false;
                        }
                        System.out.println(nodeName + (isLeader ? " I am leader node" : "I am not leader node"));
                    }
                }, null);

    }

    private void checkForLeaderSelectionFinish() {
        final String serverId = nodeName;
        zooKeeper.getData(MASTER_ZK_PATH, false,
                new AsyncCallback.DataCallback() {
                        @Override
                        public void processResult(int returnCode, String path, Object ctx, byte[] bytes, Stat stat) {
                            switch (KeeperException.Code.get(returnCode)){
                                case CONNECTIONLOSS:
                                    checkForLeaderSelectionFinish();
                                    break;
                                case NONODE:
                                    leaderSelection();
                                    break;
                                default:
                                    isLeader = serverId.equals(new String(bytes));
                            }
                        }
                    },
                null);
    }

    public void stopNode() throws InterruptedException {
        zooKeeper.close();
    }

    private void initZkNodes(String nodename) {
        asyncCreateNode(nodename , "/workers", "");

        asyncCreateNode(nodename , "/assign", "");
        asyncCreateNode(nodename , "/tasks", "");

    }

    private void asyncCreateNode(String createdIp, String path, final String data) {
        zooKeeper.create(path,
                data.getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT,
                new AsyncCallback.StringCallback(){
                    @Override
                    public void processResult(int returnCode, String path, Object ctx, String name) {
                        switch (KeeperException.Code.get(returnCode)){
                            case CONNECTIONLOSS:
                                asyncCreateNode(createdIp, path, data);
                                break;
                            case OK:
                                System.out.println(createdIp + " Create " + path + " with data " + data + " success!");
                                break;
                            case NODEEXISTS:
                                System.out.println("Path " + path + " had exists!");
                                break;
                            default:
                                System.out.println("ERROR " + KeeperException.create(KeeperException.Code.get(returnCode), path));
                        }
                    }
                }, data);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final AtomicInteger id = new AtomicInteger(0);
        Runnable run = ()->{
            String nodeName = "192.168.0." + id.incrementAndGet();
            MasterNodeAsyncMode masterNote = new MasterNodeAsyncMode("127.0.0.1:2181", nodeName);
            System.out.println("current Node server id is : " + nodeName);
            try {
                masterNote.startNode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(nodeName + " Start node selection ......");
            masterNote.leaderSelection();

            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                masterNote.stopNode();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i <3 ; i++) {
            new Thread(run).start();
        }
        

    }
}

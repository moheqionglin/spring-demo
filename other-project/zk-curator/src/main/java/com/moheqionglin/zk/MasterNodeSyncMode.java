package com.moheqionglin.zk;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class MasterNodeSyncMode implements Watcher {

    private String server;
    private String nodeName;
    private ZooKeeper zooKeeper;
    private boolean isLeader;
    private final String MASTER_ZK_PATH = "/master";

    public MasterNodeSyncMode(String server, String nodeName) {
        this.server = server;
        this.nodeName = nodeName;
    }

    public void startNode() throws IOException {
        zooKeeper = new ZooKeeper(server, 10000, this);
    }

    /*
    *  @return 是否是leader
    *
    * 1. 我们尝试创建 /master， 同时在/master中保存当前机器的唯一标识
    *     1.1 如果 /master已经存在，那么创建失败
    *     1.2
    * */
    public void leaderSelection() throws InterruptedException {
        for(;;){
            try {
                String s = zooKeeper.create(MASTER_ZK_PATH, nodeName.getBytes(StandardCharsets.UTF_8),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                System.out.println(s);

            } catch (KeeperException.NodeExistsException e) {
                isLeader = false;
                return;
            } catch (KeeperException.ConnectionLossException e){
                //连接丢失异常，可能因为各种原因产生，比如网络问题，这个异常 客户端不知道服务端是否已经 成功创建爱你master，因此需要后续的checkForLeader处理。因此这个异常我们不作处理。
            } catch (KeeperException e) {
                //
            }

            if(checkForLeaderSelectionFinish()){
                break;
            }
        }
    }

    /**
     * @return 是否已经选举结束
     * */
    private boolean checkForLeaderSelectionFinish() throws InterruptedException {

        for(;;){
            Stat stat = new Stat();
            try {
                byte[] data = zooKeeper.getData(MASTER_ZK_PATH, false, stat);
                isLeader = this.nodeName.equals(new String(data));
                return true;
            } catch (KeeperException.NoNodeException e) {
                isLeader = false;
                return false;
            } catch (KeeperException.ConnectionLossException e) {

            }catch (KeeperException e){

            }

        }
    }

    public void stopNode() throws InterruptedException {
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    /**
     * /workers/worker-1 : busy | free
     * /tasks/task-1 : done | processing
     * /assign/worker-1/task-1
     *
     * 1. Client节点 不断地提交task到zk的 /tasks列表中。
     * 2. Master节点 监控/tasks节点，发现有新的task上传的时候，就下发task给空闲的workers节点。
     * 3. Worker节点 监控 /asign/worker-n 发现有新的task指派的时候，就开始工作。
     * */
    private void initZkNodes() {
        asyncCreateNode("/workers", "");
        asyncCreateNode("/assign", "");
        asyncCreateNode("/tasks", "");

    }

    private void asyncCreateNode(String path, final String data) {
        zooKeeper.create(path,
                data.getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT,
                new AsyncCallback.StringCallback(){
                    @Override
                    public void processResult(int returnCode, String path, Object ctx, String name) {
                        switch (KeeperException.Code.get(returnCode)){
                            case CONNECTIONLOSS:
                                asyncCreateNode(path, data);
                                break;
                            case OK:
                                System.out.println("Create " + path + " with data " + data + " success!");
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

    /**
     * 1. 如果 程序在没有主动zookeeper.close()的情况下，因为各种原因崩溃了，那么临时节点 /master不会立即消失，而是会等到 设置的超时时间以后消失。
     *
     *
     * */
    public static void main(String[] args) throws IOException, InterruptedException {
        Runnable run = ()->{
            String nodeName = "192.168.0." + new Random().nextInt(254);
            MasterNodeSyncMode masterNote = new MasterNodeSyncMode("127.0.0.1:2181", nodeName);
            System.out.println("current Node server id is : " + nodeName);
            try {
                masterNote.startNode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Start node selection ......");
            try {
                masterNote.leaderSelection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(nodeName + (masterNote.isLeader ? "I am leader node" : "I am not leader node") );

            if(masterNote.isLeader){
                masterNote.initZkNodes();
            }

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

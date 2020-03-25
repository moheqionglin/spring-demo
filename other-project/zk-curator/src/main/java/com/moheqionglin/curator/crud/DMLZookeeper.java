package com.moheqionglin.curator.crud;

import com.moheqionglin.curator.CuratorFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundPathable;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName : DMLZookeeper
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-23 17:58
 */
public class DMLZookeeper {

    private CuratorFramework client = CuratorFactory.getCuratorFramework("");
    /**
     * create -e -s /path ""
     */
    public void createPath(String path, boolean isTemp, boolean isSequence, String data){
        try {
            CreateMode mode = isTemp && isSequence ? CreateMode.EPHEMERAL_SEQUENTIAL :
                    isTemp && !isSequence ? CreateMode.EPHEMERAL :
                            !isTemp && isSequence ? CreateMode.PERSISTENT_SEQUENTIAL :
                                    CreateMode.PERSISTENT;
            client.create().creatingParentsIfNeeded()
                    .withMode(mode)
                    .forPath(path, data .getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * rmr /path
     */
    public void deletePath(String path, int version){
        try {
            client.delete().guaranteed()
                    .deletingChildrenIfNeeded()
                    .withVersion(version)
                    .forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * set /path "data"
     */
    public void updatePath(String path, String data, int version){
        try {
            client.setData().withVersion(version)
                    .forPath(path, data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * get /path
     */
    public void getData(String path){
        try {
            byte[] bytes = client.getData().forPath(path);
            System.out.println(">>getdata> " + new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * ls /path 获取子节点
     */
    public void getChild(String path){
        try {
            List<String> strings = client.getChildren().forPath(path);
            System.out.println(">>child > " + strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * exists /path 获取子节点
     */
    public void exists(String path){
        try {
            Stat stat = client.checkExists().forPath(path);
            System.out.println(">>exists > " + stat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * stat /path
     */
    public void stat(String path){
        try {
            Stat stat = new Stat();
            byte[] bytes = client.getData().storingStatIn(stat).forPath(path);
            System.out.println(">>stat> " + new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * watch data
     */
    public void watchData(String path, Watcher task){
        try {
            client.getData()
                    .usingWatcher(task)
                    .forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * watch path
     */
    public void watchPath(String path, Watcher task){
        try {
            client.getChildren()
                    .usingWatcher(task)
                    .forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 一直循环监控
     * @param path
     */
    public void pathChildrenCache(String path){
        PathChildrenCache workerCache = new PathChildrenCache(client, path, true);
        try {
            workerCache.start();
            workerCache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) {
                    try {
                        PathChildrenCacheEvent.Type eventType = event.getType();
                        switch (eventType) {
                            case CONNECTION_RECONNECTED:
                                System.out.println(path + " path Connection recconnected!");
                                break;
                            case CONNECTION_SUSPENDED:
                                System.out.println(path + " path Connection suspended, waiting...");
                                break;
                            case CONNECTION_LOST:
                                System.out.println(path + " path Connection lost, waiting...");
                                break;
                            case CHILD_ADDED: {
                                String path = ZKPaths.getNodeFromPath(event.getData().getPath());
                                System.out.println(path + " path is added: " + URLDecoder.decode(path, "UTF-8"));
                                break;
                            }
                            case CHILD_UPDATED: {
                                System.out.println(path + " path is changed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
                                break;
                            }
                            case CHILD_REMOVED: {
                                String path = ZKPaths.getNodeFromPath(event.getData().getPath());
                                System.out.println(path + " path is removed: " + URLDecoder.decode(path, "UTF-8"));
                                break;
                            }
                            default:
                                System.out.println(path + " path Data:" + event.getData().toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        DMLZookeeper dMLZookeeper = new DMLZookeeper();

        dMLZookeeper.createPath("/java/delete", false, false,"data");
        dMLZookeeper.createPath("/java/child", false, false,"child-data");

        dMLZookeeper.watchData("/java/child", new Watcher(){
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("=====>wathcer触发了。。。。");
                System.out.println(watchedEvent);
            }
        });
        dMLZookeeper.watchPath("/java/child", new Watcher(){
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("=====>wathcer触发了。。。。");
                System.out.println(watchedEvent);
            }
        });
        dMLZookeeper.watchPath("/java/delete", new Watcher(){
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("=====>wathcer触发了。。。。");
                System.out.println(watchedEvent);
            }
        });
        dMLZookeeper.exists("/java/child");
        dMLZookeeper.stat("/java/child");
        dMLZookeeper.getData("/java/child");
        System.out.println("---------------------");
        dMLZookeeper.updatePath("/java/child", "new-data", 0);
        dMLZookeeper.stat("/java/child");
        dMLZookeeper.getChild("/java/child");
        dMLZookeeper.getData("/java/child");

        dMLZookeeper.deletePath("/java/delete", 0);

        dMLZookeeper.exists("/java/child");
        dMLZookeeper.pathChildrenCache("/java/child");
        new CountDownLatch(1).await();
    }


}
package com.moheqionglin.curator;

import com.google.common.base.Charsets;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.UnhandledErrorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author wanli.zhou
 * @description
 * @time 11/10/2018 10:14 AM
 */
public class CuratorDemo {

    private final static String zk = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        final CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(
                zk, 30000, 6000, new ExponentialBackoffRetry(1000, 3));
        //Curator 的监听器
        registerListener(curatorFramework);

        curatorFramework.start();
        //创建sequence persistent类型的子节点，同时添加监听器
        addChildRenWatcherAndCreateSequenceNode(curatorFramework);

        createParentIfNeedWithProtection(curatorFramework);
        Thread.sleep(100000);

    }

    public static void createParentIfNeedWithProtection(final CuratorFramework curatorFramework){
        try {
            curatorFramework.create().creatingParentsIfNeeded().forPath("/parent/children");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerListener(CuratorFramework curatorFramework) {
        curatorFramework.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                switch (curatorEvent.getType()){
                    case CHILDREN:
                        System.out.println("curatorEvent CHILDREN : " + curatorEvent.getChildren());
                    case CREATE:
                        System.out.println("curatorEvent CREATE : " + curatorEvent.getPath());
                }
            }
        });
        curatorFramework.getUnhandledErrorListenable().addListener(new UnhandledErrorListener() {
            @Override
            public void unhandledError(String s, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public static void addChildRenWatcherAndCreateSequenceNode(final CuratorFramework curatorFramework){
        try {
            curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/bbb");
            curatorFramework.getChildren().watched().forPath("/bbb");
            setWatch(curatorFramework);
        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i = 0 ; i < 10 ; i ++){
            try {
                curatorFramework.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/bbb/eee", ("-" + i).getBytes(Charsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setWatch(final CuratorFramework curatorFramework) throws Exception {
        curatorFramework.getChildren().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
                    try {
                        System.out.println("==>改变： " + watchedEvent.getPath() + "\n" + watchedEvent.getState() + "\n" + curatorFramework.getChildren().forPath("/bbb"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        setWatch(curatorFramework);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).forPath("/bbb");
    }


}
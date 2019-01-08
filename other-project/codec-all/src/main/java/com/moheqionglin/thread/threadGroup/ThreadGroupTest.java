package com.moheqionglin.thread.threadGroup;

/**
 * @author wanli.zhou
 * @description
 * @time 13/11/2018 7:36 PM
 */
public class ThreadGroupTest {
    //https://www.toutiao.com/i6622891348036944391/?tt_from=weixin&utm_campaign=client_share&wxshare_count=1&from=singlemessage&timestamp=1542108885&app=news_article_lite&utm_source=weixin&iid=50412815813&utm_medium=toutiao_android&group_id=6622891348036944391
    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println("牛旦IT课堂线程线程组名称：" + Thread.currentThread().getThreadGroup());
            System.out.println("牛旦IT课堂线程线程名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        ThreadGroup userGroup = new ThreadGroup("user");
        userGroup.setMaxPriority(Thread.MIN_PRIORITY);
        Thread userTask1 = new Thread(userGroup, runnable, "user-task1");
        Thread userTask2 = new Thread(userGroup, runnable, "user-task2");
        userTask1.start();
        userTask2.start();
        System.out.println("牛旦IT课堂线程线程组活跃线程数：" + userGroup.activeCount());
        userGroup.list();
    }
}
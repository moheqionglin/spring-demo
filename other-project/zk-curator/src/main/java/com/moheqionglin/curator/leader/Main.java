package com.moheqionglin.curator.leader;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : Main
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-24 16:20
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        final List<LeaderCurator> ls = new ArrayList<>();
        final  List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 10 ; i ++){
            final String name = "192.168.0.1" + i;
            LeaderCurator l = new LeaderCurator(name);

            ls.add(l);
        }

        for (int i = 0 ; i < 10 ; i ++){
           final int j = i;
            Thread thread = new Thread(() -> {
                ls.get(j).selectLeader();
            });
            threads.add(thread);
            thread.start();
        }

        System.out.println("每隔三秒换一次leader");
        for(int j = 0; j < 10; j ++){

            Thread.sleep(3 * 1000);
            for(int i = 0 ; i < 10 ; i ++){
                if(ls.get(i).isLeader()){
                    System.out.println("【【3秒后， 中断一个leader "+ls.get(i).getIp()+" 】】");
                    ls.get(i).stop();
                    break;
                }
            }
        }

    }
}
package com.moheqionglin.nettyFull;

import io.netty.channel.nio.NioEventLoop;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author wanli.zhou
 * @description
 * @time 14/11/2018 7:45 PM
 */
public class NioEventLoopDemo {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> submit = executorService.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            return "xxx";
        });

    }

}
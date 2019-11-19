package com.moheqionglin.sqlParser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-10 18:58
 */
public class Test {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i = 0 ; i < 100 ; i ++){
            executorService.submit(()->{
                new T3().a();
            });
        }
    }
}
package com.moheqionglin.dubboSingleTcp;

import java.util.Random;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-29 11:37
 */
public class DubboNettySocketHandler {

    private Random random = new Random();
    public void select() throws InterruptedException {
        //不断的select
        // 收到消息，反序列化
        RequestResult requestResult= new RequestResult("结果-1");
        //获取 requestId
        int requestId = 1;
        int time = random.nextInt(10) * 1000;
        System.out.println("DubboNettySocketHandler sleep " + time);
        Thread.sleep(time);
        ResponseHolder.result.put(requestId, requestResult);
        RequestFutureHolder.requestResultMap.get(requestId).single();
    }

}

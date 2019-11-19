package com.moheqionglin.dubboSingleTcp;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-29 11:28
 */
public class CustomDubboRemoveInvoke {

    private AtomicInteger requestId = new AtomicInteger(0);

    public CustomDubboResponseFuture invoke(String server, Invocation invocation) throws InterruptedException {
        //1. 从Map中获取已存在 TCP socket链接
        //2. 序列化
        //3. 获取requestID， 发送 message 给 server
        //4. 返回future

        CustomDubboResponseFuture customDubboResponseFuture =  new CustomDubboResponseFuture();
        int rid = requestId.incrementAndGet();
        customDubboResponseFuture.setRequestId(rid);
        RequestFutureHolder.requestResultMap.put(rid, customDubboResponseFuture);

        return customDubboResponseFuture;
    }
}
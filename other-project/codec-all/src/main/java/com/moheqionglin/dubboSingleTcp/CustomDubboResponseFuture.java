package com.moheqionglin.dubboSingleTcp;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-29 11:23
 */
public class CustomDubboResponseFuture {
    Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();
    int requestId = -1;

    public Object get() throws InterruptedException {
        try{
            lock.lock();
            condition.await();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

        return ResponseHolder.result.get(requestId);
    }

    public void single(){
        try{
            lock.lock();
            condition.signal();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
    }
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
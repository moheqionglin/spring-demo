package com.moheqionglin.aqs;

import org.springframework.stereotype.Component;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-22 16:24
 *
 * 公平锁实现方式
 */
@Component
public class CustomAQSLock {
    private volatile int state = 0;
    private ConcurrentLinkedQueue<Thread> waiter = new ConcurrentLinkedQueue<Thread>();
    private Thread lockHolderThread = null;
    private static final long stateFieldOffset;

    private static Unsafe unsafe = reflectGetUnsafe();

    static {
        try {
            stateFieldOffset = unsafe.staticFieldOffset(CustomAQSLock.class.getField("state"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    public boolean acquire(){
        Thread thread = Thread.currentThread();
        int s = getState();
        if(s == 0){
            //公平锁, 所以获取的时候要从队列中拿到线程
            if((waiter.isEmpty() || thread == waiter.peek()) && casSate(0, 1)){
                setLockHolderThread(thread);
                return true;
            }
        }
        return false;
    }

    public void lock(){
        Thread current = Thread.currentThread();
        if(acquire()){
            return;
        }

        //加锁失败, 队列等待
        waiter.add(current);
        //1. 为什么不用自旋锁？或者Sleep，或者yeild？ 因为 不想占用CPU。所以用队列缓存线程。
        //2. 为什么不用Object.await ? 而是用LockSupport.park(current) ？ 因为想精确唤醒明确的队列中的某个线程。而notify是随机唤醒的。
        for(;;){
            //公平锁的实现方式，所以要排队拿锁
            if(current == waiter.peek() && acquire()){
                waiter.poll();
                return;
            }
            LockSupport.park(current);
        }
    }

    public void unlock(){
        Thread current = Thread.currentThread();
        if(current != getLockHolderThread()){
            throw new RuntimeException("current is not lock holder thread");
        }

        if(casSate(getState(), 0)){
            setLockHolderThread(null);
            Thread nextThread = waiter.peek();
            if(nextThread != null){
                LockSupport.unpark(nextThread);
            }
        }

    }

    private boolean casSate(int expected, int newVal){
        return unsafe.compareAndSwapInt(this, stateFieldOffset, expected, newVal);
    }

    public static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setLockHolderThread(Thread lockHolderThread) {
        this.lockHolderThread = lockHolderThread;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Thread getLockHolderThread() {
        return lockHolderThread;
    }
}